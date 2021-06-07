package com.maku.pombe.ui.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maku.pombe.R

class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }
}