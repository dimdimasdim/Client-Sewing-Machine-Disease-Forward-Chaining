package com.dimas.networkexercise.base

import android.app.Application
import com.dimas.networkexercise.utils.ApplicationProviderUtils

class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        ApplicationProviderUtils.initialize(this)
    }

}