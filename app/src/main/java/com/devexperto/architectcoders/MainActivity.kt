package com.devexperto.architectcoders

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            val movies = RemoteConnection.service.listPopularMovies(getString(R.string.api_key))
            toast(movies.results.size.toString())
        }
    }
}