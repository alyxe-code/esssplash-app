package com.p2lem8dev.esssplash.home

import dagger.Module
import dagger.Provides

@Module
class HomeModule {
    @Provides
    fun homeRepository(): HomeRepository = HomeRepositoryImpl()
}
