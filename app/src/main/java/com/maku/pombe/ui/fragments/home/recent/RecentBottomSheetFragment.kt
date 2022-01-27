package com.maku.pombe.ui.fragments.home.recent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.maku.pombe.bindingadapter.RecentRowBinding.Companion.loadImageFromUrl
import com.maku.pombe.databinding.FragmentRecentBottomSheetBinding
import com.maku.pombe.vm.SharedViewModel
import timber.log.Timber

class RecentBottomSheetFragment : BottomSheetDialogFragment() {

    private val model: SharedViewModel by activityViewModels()

    private var _binding: FragmentRecentBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentRecentBottomSheetBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        model.selected.observe(viewLifecycleOwner, { item ->
            // Update the UI
            Timber.d("recent drink %s",  item)
            loadImageFromUrl(binding.mainBottomImageView, item.strDrinkThumb)
            binding.recentTitle.text = item.strDrink
            binding.srtAlcoholic.text = item.strAlcoholic
            binding.strCategory.text = item.strCategory
            binding.instructionsResult.text = item.strInstructions
            binding.ingone.text = item.strIngredient1
            binding.ingtwo.text = item.strIngredient2
            binding.ingthree.text = item.strIngredient3
            binding.ingFour.text = item.strIngredient4
            binding.glass.text = item.strGlass

        })

        return binding.root
    }

}