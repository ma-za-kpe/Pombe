package com.maku.pombe.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maku.pombe.data.models.recent.Drink

class SharedViewModel : ViewModel() {
    val selected = MutableLiveData<Drink>()

    fun select(item: Drink) {
        selected.value = item
    }
}