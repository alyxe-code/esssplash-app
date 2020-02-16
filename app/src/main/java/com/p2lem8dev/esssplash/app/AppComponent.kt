package com.p2lem8dev.esssplash.app

import android.app.Application
import com.p2lem8dev.esssplash.home.HomeModule
import com.p2lem8dev.esssplash.home.HomeRepository
import dagger.Component

@Component(
    dependencies = [
        Application::class,
        RetrofitProvider::class
    ],
    modules = [
        AppModule::class,
        HomeModule::class
    ]
)
interface AppComponent {
    fun home(): HomeRepository
}
