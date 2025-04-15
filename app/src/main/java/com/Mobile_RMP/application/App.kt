package com.Mobile_RMP.application

import android.app.Application
import com.Mobile_RMP.application.utils.AuthPreferences

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AuthPreferences.init(this)
    }
}