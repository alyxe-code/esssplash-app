package com.p2lem8dev.esssplash.app

import android.app.Application
import com.p2lem8dev.unsplashapi.UnsplashModule
import com.p2lem8dev.unsplashapi.repository.PhotosRepository
import dagger.Component

@Component(
    dependencies = [
        Application::class
    ],
    modules = [
        AppModule::class,
        UnsplashModule::class
    ]
)
interface AppComponent {
    fun photos(): PhotosRepository
}
