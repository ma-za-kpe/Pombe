package com.maku.pombe.ui.fragments.home.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.maku.core.domain.popular.Drink
import com.maku.pombe.databinding.FragmentPopularBottomBinding
import com.maku.pombe.ui.fragments.home.adapters.PopularCocktailAdapter
import com.maku.core.utils.NetworkListener
import com.maku.core.utils.NetworkResult
import com.maku.core.utils.observeOnce
import com.maku.pombe.vm.HomeViewModel
import com.maku.pombe.vm.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class PopularBottomFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentPopularBottomBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var homeViewModel: HomeViewModel


    private val mPopularAdapter by lazy { PopularCocktailAdapter { item ->
        viewDetails(item as Drink)
    }
    }

    private fun viewDetails(drink: Drink) {
        Timber.d("all popular cocktails $drink")
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentPopularBottomBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.popularall = mainViewModel

        setupPopularRecyclerView()

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

    private fun readDatabase() {
        lifecycleScope.launch {

            mainViewModel.readPopularCocktails.observeOnce(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    Timber.d("readDatabase called!")
                    mPopularAdapter.setData(database[0].popular)
                    hideShimmerEffect()
                } else {
                    requestPopularApiData()
                }
            })

        }
    }

    private fun requestPopularApiData() {
        mainViewModel.getPopularCocktails()
        mainViewModel.popularCocktailResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mPopularAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    loadPopularDataFromCache()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
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

    private fun setupPopularRecyclerView() {
        binding.popularAll.adapter = mPopularAdapter
        binding.popularAll.layoutManager =   LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    }

    private fun showShimmerEffect() {
        binding.popularAll.showShimmer()
    }

    private fun hideShimmerEffect(){
        binding.popularAll.hideShimmer()
    }

}