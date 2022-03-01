package com.example.mobilecomputinghw

import android.app.Application

class MobileComputingHWApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}