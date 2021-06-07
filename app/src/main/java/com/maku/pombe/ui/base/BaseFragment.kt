package com.maku.pombe.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.maku.pombe.R
import com.maku.pombe.utils.NetworkListener
import com.maku.pombe.utils.NetworkResult
import com.maku.pombe.utils.observeOnce
import com.maku.pombe.vm.HomeViewModel
import com.maku.pombe.vm.MainViewModel
import com.todkars.shimmer.ShimmerRecyclerView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

open class BaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_base, container, false)

        return view.rootView
    }

}