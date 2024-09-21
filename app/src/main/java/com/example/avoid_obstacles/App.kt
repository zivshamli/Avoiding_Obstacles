package com.example.avoid_obstacles

import android.app.Application
import com.example.avoid_obstacles.utilities.SharedPreferencesManagerV3
import com.example.avoid_obstacles.utilities.SignalManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPreferencesManagerV3.init(this)
        SignalManager.init(this)
    }

}