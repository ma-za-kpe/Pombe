package com.maku.pombe.ui.fragments.home.popular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.maku.pombe.R
import com.maku.pombe.databinding.FragmentDetailsBinding
import com.maku.pombe.databinding.FragmentPopularBottomBinding
import com.maku.pombe.vm.SharedViewModel

class PopularBottomFragment : BottomSheetDialogFragment() {

    private val model: SharedViewModel by activityViewModels()

    private var _binding: FragmentPopularBottomBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentPopularBottomBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

}