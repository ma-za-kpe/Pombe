package com.maku.pombe.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.maku.pombe.databinding.FragmentHomeBinding
import com.maku.pombe.ui.fragments.home.adapters.RecentCocktailAdapter
import com.maku.pombe.utils.NetworkResult
import com.maku.pombe.vm.HomeViewModel
import com.maku.pombe.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var homeViewModel: HomeViewModel
    private val mAdapter by lazy { RecentCocktailAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
    }


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
        requestApiData()

        return binding.root
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
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        })
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