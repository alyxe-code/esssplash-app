package com.p2lem8dev.esssplash.app

import android.app.Application
import com.p2lem8dev.esssplash.photos.PhotosModule
import com.p2lem8dev.esssplash.photos.PhotosRepository
import dagger.Component

@Component(
    dependencies = [
        Application::class,
        RetrofitProvider::class
    ],
    modules = [
        AppModule::class,
        PhotosModule::class
    ]
)
interface AppComponent {
    fun home(): PhotosRepository
}
