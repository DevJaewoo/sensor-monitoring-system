package com.devjaewoo.sensormonitoringapp

import android.app.Application
import android.content.Context

class ApplicationManager : Application() {

    companion object {
        lateinit var applicationContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        ApplicationManager.applicationContext = applicationContext
    }
}