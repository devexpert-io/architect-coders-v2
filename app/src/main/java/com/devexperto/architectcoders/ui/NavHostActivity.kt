package com.devexperto.architectcoders.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.devexperto.architectcoders.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavHostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_host)
    }
}