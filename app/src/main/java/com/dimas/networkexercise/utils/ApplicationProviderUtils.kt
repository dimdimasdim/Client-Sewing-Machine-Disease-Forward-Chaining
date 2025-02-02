package com.dimas.networkexercise.utils

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object ApplicationProviderUtils {
    private var context: Context? = null


    private fun init(context: Context) {
        ApplicationProviderUtils.context = context
    }

    private fun getContext(): Context {
        if (context == null) {
            throw IllegalStateException("call init first")
        }
        return context!!
    }


    @JvmStatic
    fun initialize(context: Context) {
        init(context)
    }

    @JvmStatic
    fun get(): Context {
        return getContext()
    }
}