package com.p2lem8dev.esssplash.app

import android.app.Application
import com.p2lem8dev.esssplash.photos.PhotosModule
import com.p2lem8dev.esssplash.photos.PhotosRepository
import com.p2lem8dev.esssplash.search.SearchModule
import com.p2lem8dev.esssplash.search.SearchRepository
import dagger.Component

@Component(
    dependencies = [
        Application::class,
        RetrofitProvider::class
    ],
    modules = [
        AppModule::class,
        PhotosModule::class,
        SearchModule::class
    ]
)
interface AppComponent {
    fun home(): PhotosRepository
    fun search(): SearchRepository
}
