package com.p2lem8dev.esssplash.search

import dagger.Module

@Module
class SearchModule {
    fun repository(): SearchRepository = SearchRepositoryImpl()
}
