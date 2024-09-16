package com.example.avoid_obstacles

import android.app.Application
import com.example.avoid_obstacles.utilities.SharedPreferencesManagerV3

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPreferencesManagerV3.init(this)
    }

}