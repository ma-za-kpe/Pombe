package com.maku.pombe.common.presentation.m

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.maku.logging.Logger
import com.maku.pombe.R
import com.maku.pombe.databinding.FragmentFirst2Binding
import com.maku.pombe.popularfeature.presentation.PopularDrinkEvent
import com.maku.pombe.popularfeature.presentation.PopularFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class First2Fragment : Fragment() {

    private val viewModel: PopularFragmentViewModel by viewModels()
    private var _binding: FragmentFirst2Binding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirst2Binding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        requestInitialAnimalsList()
    }

    private fun setupUI() {
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_First2Fragment_to_Second2Fragment)
        }
        observeViewStateUpdates()
    }

    private fun observeViewStateUpdates() {
        viewModel.state.observe(viewLifecycleOwner) {
            Logger.d("data from server : ${it.drinks}")
            binding.textviewFirst.text = it.drinks.toString()
        }
    }

    private fun requestInitialAnimalsList() {
        viewModel.onEvent(PopularDrinkEvent.RequestPopularDrinksList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}