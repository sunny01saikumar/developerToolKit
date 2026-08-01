package com.devtoolkit.pro

import android.app.Application
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DevToolkitApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize the Mobile Ads SDK.
        try {
            MobileAds.initialize(this) {}
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
