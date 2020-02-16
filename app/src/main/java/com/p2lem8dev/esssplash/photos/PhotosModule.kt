package com.p2lem8dev.esssplash.photos

import dagger.Module
import dagger.Provides

@Module
class PhotosModule {
    @Provides
    fun homeRepository(api: PhotosApi): PhotosRepository = PhotosRepositoryImpl(api)
}
