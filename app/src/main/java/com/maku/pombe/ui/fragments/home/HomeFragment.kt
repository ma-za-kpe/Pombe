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
import com.maku.pombe.data.models.recent.Drink
import com.maku.pombe.databinding.FragmentHomeBinding
import com.maku.pombe.ui.fragments.home.adapters.RecentCocktailAdapter
import com.maku.pombe.utils.NetworkListener
import com.maku.pombe.utils.NetworkResult
import com.maku.pombe.utils.observeOnce
import com.maku.pombe.vm.HomeViewModel
import com.maku.pombe.vm.MainViewModel
import com.maku.pombe.vm.SharedViewModel
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
    private val mAdapter by lazy { RecentCocktailAdapter { item ->
        openBottomSheet(item as Drink)
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

        mainViewModel =
                ViewModelProvider(this).get(MainViewModel::class.java)

        setupRecyclerView()
        binding.viewone.setOnClickListener {
            openRecentViewAllFragment()
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

    private fun openRecentViewAllFragment() {
        if ( homeViewModel.networkStatus){
            findNavController().navigate(R.id.action_navigation_home_to_recentFragment)
        } else {
            homeViewModel.showNetworkStatus()
        }
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecentCocktails.observeOnce(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    Timber.d("readDatabase called!")
                    mAdapter.setData(database[0].recent)
                    hideShimmerEffect()
                } else {
                    requestApiData()
                }
            })
        }
    }

    private fun requestApiData() {
        mainViewModel.getRecentCocktails()
        mainViewModel.recentCocktailResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    loadDataFromCache()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
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
        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        binding.recent.showShimmer()
    }

    private fun hideShimmerEffect(){
        binding.recent.hideShimmer()
    }
}