package com.p2lem8dev.esssplash.app

import android.app.Application
import android.util.Log
import com.p2lem8dev.esssplash.BuildConfig
import com.p2lem8dev.unsplashapi.UnsplashModule

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()

        val isDebug = BuildConfig.DEBUG

        val unsplashModule = UnsplashModule(
            appKey = Constants.ACCESS_KEY,
            secretKey = Constants.SECRET_KEY,
            isDebug = isDebug,
            httpLogger = { message -> Log.d("OkHttp", message) }
        )

        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .unsplashModule(unsplashModule)
            .build()
    }
}
