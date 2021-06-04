package com.maku.pombe.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.maku.pombe.utils.Constants.Companion.API_KEY

class HomeViewModel (application: Application) : AndroidViewModel(application) {

    fun applyQueries(): Int {

        return API_KEY
    }

}