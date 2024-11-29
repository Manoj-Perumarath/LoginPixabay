package com.example.loginpixabay

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PixabayApp :Application() {

    override fun onCreate() {
        super.onCreate()

    }
}