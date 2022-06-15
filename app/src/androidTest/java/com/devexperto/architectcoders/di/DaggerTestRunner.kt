package com.devexperto.architectcoders.di

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.devexperto.architectcoders.TestApp
import kotlinx.coroutines.ExperimentalCoroutinesApi

// A custom runner to set up the instrumented application class for tests.
@ExperimentalCoroutinesApi
@Suppress("unused")
class DaggerTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?)
            : Application {
        return super.newApplication(cl, TestApp::class.java.name, context)
    }
}