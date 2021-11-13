package com.mavenusrs.vehicles

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VehicleApp: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}