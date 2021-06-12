package com.maku.pombe.ui.fragments.home.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.maku.pombe.bindingadapter.RecentRowBinding
import com.maku.pombe.databinding.FragmentDetailsBinding
import com.maku.pombe.ui.activities.MainActivity
import com.maku.pombe.vm.SharedViewModel
import timber.log.Timber


class DetailsFragment : Fragment() {

    private val model: SharedViewModel by activityViewModels()

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        (activity as MainActivity?)!!.supportActionBar!!.hide()

        model.selectedPopular.observe(viewLifecycleOwner, { item ->
            // Update the UI
            Timber.d("recent drink %s", item)
            RecentRowBinding.loadImageFromUrl(binding.detailsImageView, item.strDrinkThumb)
            binding.detailTitle.text = item.strDrink
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