package com.p2lem8dev.esssplash.app

import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun isDebuggable(): Boolean = true
}
