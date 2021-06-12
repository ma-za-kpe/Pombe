package com.maku.pombe.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maku.pombe.data.models.recent.Drink

class SharedViewModel : ViewModel() {

    // recent
    val selected = MutableLiveData<Drink>()

    fun select(item: Drink) {
        selected.value = item
    }

    // popular
    val selectedPopular = MutableLiveData<com.maku.pombe.data.models.popular.Drink>()

    fun selectPopular(item: com.maku.pombe.data.models.popular.Drink) {
        selectedPopular.value = item
    }
}