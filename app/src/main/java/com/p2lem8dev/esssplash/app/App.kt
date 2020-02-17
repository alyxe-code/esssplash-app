package com.p2lem8dev.esssplash.app

import android.app.Application
import com.p2lem8dev.unsplashapi.UnsplashModule

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()

        val unsplashModule = UnsplashModule(Constants.ACCESS_KEY, Constants.SECRET_KEY)

        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .unsplashModule(unsplashModule)
            .build()
    }
}
