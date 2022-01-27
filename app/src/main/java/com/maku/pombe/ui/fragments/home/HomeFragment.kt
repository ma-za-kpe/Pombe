package com.maku.pombe.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.maku.pombe.R
import com.maku.core.domain.recent.Drink
import com.maku.pombe.databinding.FragmentHomeBinding
import com.maku.pombe.ui.fragments.home.adapters.LatestCocktailAdapter
import com.maku.pombe.ui.fragments.home.adapters.PopularCocktailAdapter
import com.maku.pombe.ui.fragments.home.adapters.RecentCocktailAdapter
import com.maku.core.utils.NetworkListener
import com.maku.core.utils.NetworkResult
import com.maku.core.utils.observeOnce
import com.maku.pombe.vm.HomeViewModel
import com.maku.pombe.vm.MainViewModel
import com.maku.pombe.vm.SharedViewModel
import com.todkars.shimmer.ShimmerRecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var homeViewModel: HomeViewModel
    private val model: SharedViewModel by activityViewModels()
    private val mLatestAdapter by lazy { LatestCocktailAdapter { item ->
        openLatest(item as Drink)
    }
    }

    private fun openLatest(drink: Drink) {
        Timber.d("latest : $drink")
    }

    private val mAdapter by lazy { RecentCocktailAdapter { item ->
        openBottomSheet(item as Drink)
    }
    }

    private val mPopularAdapter by lazy { PopularCocktailAdapter { item ->
        openDialog(item as com.maku.core.domain.popular.Drink)
    }
    }

    private fun openDialog(drink: com.maku.core.domain.popular.Drink) {
        // Toast.makeText(requireContext(), "popular : ${drink.strDrink}", Toast.LENGTH_SHORT).show()
        if ( homeViewModel.networkStatus){
            model.selectPopular(drink)
            findNavController().navigate(R.id.action_navigation_home_to_detailsFragment)
        } else {
            homeViewModel.showNetworkStatus()
        }
    }

    private fun openBottomSheet(recentDrink: Drink) {
        if ( homeViewModel.networkStatus){
            model.select(recentDrink)
            findNavController().navigate(R.id.action_navigation_home_to_recentBottomSheetFragment)
        } else {
            homeViewModel.showNetworkStatus()
        }
    }

    @ExperimentalCoroutinesApi
    private lateinit var networkListener: NetworkListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
    }

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding =  FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.main = mainViewModel

        setupRecyclerView()
        setupPopularRecyclerView()
        setupLatestRecyclerView()

        binding.viewone.setOnClickListener {
            openRecentViewAllFragment()
        }

        binding.viewtwo.setOnClickListener {
            openPopularViewAllBottomFragment()
        }

        homeViewModel.readBackOnline.observe(viewLifecycleOwner, {
            homeViewModel.backOnline = it
        })


        lifecycleScope.launch {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect { status ->
                    Timber.d(status.toString())
                    homeViewModel.networkStatus = status
                    homeViewModel.showNetworkStatus()
                    readDatabase()
                }
        }

        return binding.root
    }

    private fun openPopularViewAllBottomFragment() {
        if ( homeViewModel.networkStatus){
            findNavController().navigate(R.id.action_navigation_home_to_popularBottomFragment)
        } else {
            homeViewModel.showNetworkStatus()
        }
    }

    private fun openRecentViewAllFragment() {
        if ( homeViewModel.networkStatus){
            findNavController().navigate(R.id.action_navigation_home_to_recentFragment)
        } else {
            homeViewModel.showNetworkStatus()
        }
    }

    private fun readDatabase() {
        lifecycleScope.launch {

            mainViewModel.readLatestCocktails.observeOnce(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    Timber.d("readDatabase called!")
                    mLatestAdapter.setData(database[0].latest)
                    hideShimmerEffect(binding.latest)
                } else {
                    requestLatestApiData()
                }
            })

            mainViewModel.readPopularCocktails.observeOnce(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    Timber.d("readDatabase called!")
                    mPopularAdapter.setData(database[0].popular)
                    hideShimmerEffect(binding.popular)
                } else {
                    requestPopularApiData()
                }
            })

            mainViewModel.readRecentCocktails.observeOnce(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    Timber.d("readDatabase called!")
                    mAdapter.setData(database[0].recent)
                    hideShimmerEffect(binding.recent)
                } else {
                    requestApiData()
                }
            })
        }
    }

    private fun requestLatestApiData() {
        mainViewModel.getLatestCocktails()
        mainViewModel.latestCocktailResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect(binding.latest)
                    response.data?.let { mLatestAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect(binding.latest)
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    loadLatestDataFromCache()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect(binding.latest)
                }
            }
        })
    }

    private fun loadLatestDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readLatestCocktails.observe(viewLifecycleOwner, {database->
                if (database.isNotEmpty()) {
                    mLatestAdapter.setData(database[0].latest)
                }
            })
        }
    }

    // popular
    private fun requestPopularApiData() {
        mainViewModel.getPopularCocktails()
        mainViewModel.popularCocktailResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect(binding.popular)
                    response.data?.let { mPopularAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect(binding.popular)
                    Toast.makeText(
                            requireContext(),
                            response.message.toString(),
                            Toast.LENGTH_SHORT
                    ).show()
                    loadPopularDataFromCache()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect(binding.popular)
                }
            }
        })
    }

    private fun loadPopularDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readPopularCocktails.observe(viewLifecycleOwner, {database->
                if (database.isNotEmpty()) {
                    mPopularAdapter.setData(database[0].popular)
                }
            })
        }
    }

    private fun requestApiData() {
        mainViewModel.getRecentCocktails()
        mainViewModel.recentCocktailResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect(binding.recent)
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect(binding.recent)
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    loadDataFromCache()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect(binding.recent)
                }
            }
        })
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readRecentCocktails.observe(viewLifecycleOwner, {database->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].recent)
                }
            })
        }
    }

    private fun setupRecyclerView() {
        binding.recent.adapter = mAdapter
        binding.recent.layoutManager =   LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        showShimmerEffect(binding.recent)
    }

    private fun setupPopularRecyclerView() {
        binding.popular.adapter = mPopularAdapter
        binding.popular.layoutManager =   LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        showShimmerEffect(binding.popular)
    }

    private fun setupLatestRecyclerView() {
        binding.latest.adapter = mLatestAdapter
        binding.latest.layoutManager =   LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        showShimmerEffect(binding.latest)
    }

    private fun showShimmerEffect(recyclerView: ShimmerRecyclerView) {
        recyclerView.showShimmer()
    }

    private fun hideShimmerEffect(recyclerView: ShimmerRecyclerView) {
        recyclerView.hideShimmer()
    }
}