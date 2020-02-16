package com.p2lem8dev.esssplash.app

import android.app.Application
import com.p2lem8dev.esssplash.BuildConfig

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()

        val isDebuggable = BuildConfig.DEBUG

        val retrofit = RetrofitProvider(isDebuggable)

        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .retrofitProvider(retrofit)
            .build()
    }
}
