package com.mezri.deezertestapp

import android.app.Application
import com.mezri.deezertestapp.data.network.HttpClient

class App :Application() {

    override fun onCreate() {
        super.onCreate()

        // init cache directory for http client
        HttpClient.initCacheDirectory(cacheDir)
    }
}