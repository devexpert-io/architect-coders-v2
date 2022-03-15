package com.devexperto.architectcoders

import android.app.Application
import androidx.room.Room
import com.devexperto.architectcoders.data.database.MovieDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}