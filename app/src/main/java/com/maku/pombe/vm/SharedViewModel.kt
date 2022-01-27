package com.maku.pombe.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maku.core.domain.recent.Drink

class SharedViewModel : ViewModel() {

    // recent
    val selected = MutableLiveData<Drink>()

    fun select(item: Drink) {
        selected.value = item
    }

    // popular
    val selectedPopular = MutableLiveData<com.maku.core.domain.popular.Drink>()

    fun selectPopular(item: com.maku.core.domain.popular.Drink) {
        selectedPopular.value = item
    }

    // popular bottom
    val selectedBtmPopular = MutableLiveData<com.maku.core.domain.popular.Drink>()

    fun selectBtmPopular(item: com.maku.core.domain.popular.Drink) {
        selectedBtmPopular.value = item
    }
}