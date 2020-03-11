package com.p2lem8dev.esssplash.app

import android.app.Application
import com.p2lem8dev.unsplashapi.UnsplashModule
import com.p2lem8dev.unsplashapi.repository.UnsplashPhotosRepository
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
    fun photosRepository(): UnsplashPhotosRepository
}
