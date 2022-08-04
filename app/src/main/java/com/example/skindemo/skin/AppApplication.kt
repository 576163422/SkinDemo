package com.example.skindemo.skin

import android.app.Application

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SkinManager.init(this)
    }
}