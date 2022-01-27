package com.maku.pombe.ui.fragments.home.recent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.maku.pombe.databinding.FragmentRecentBinding
import com.maku.pombe.ui.base.BaseFragment
import com.maku.pombe.ui.fragments.home.adapters.ViewAllAdapter
import com.maku.core.utils.NetworkListener
import com.maku.core.utils.NetworkResult
import com.maku.core.utils.observeOnce
import com.maku.pombe.vm.HomeViewModel
import com.maku.pombe.vm.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class RecentFragment : BaseFragment() {

    private var _binding: FragmentRecentBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recentViewModel: RecentViewModel

    @ExperimentalCoroutinesApi
    private lateinit var networkListener: NetworkListener

    private val mAdapter by lazy { ViewAllAdapter()    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        recentViewModel = ViewModelProvider(requireActivity())[RecentViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentRecentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        lifecycleScope.launch {
            recentViewModel.observeRecentLocalCocktails().observe(viewLifecycleOwner, {database->
                if (database.isNotEmpty()) {
                    Timber.d("recent use case ${database[0].recent}")
                }
            })
        }
        Timber.d("recent use case ")

        setupRecyclerView()

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
        binding.recentviewall.adapter = mAdapter
        binding.recentviewall.layoutManager =   LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        binding.recentviewall.showShimmer()
    }

    private fun hideShimmerEffect(){
        binding.recentviewall.hideShimmer()
    }

}