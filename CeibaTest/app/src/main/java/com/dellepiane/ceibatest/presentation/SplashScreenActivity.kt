package com.dellepiane.ceibatest.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dellepiane.ceibatest.R
import dagger.hilt.android.AndroidEntryPoint

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}