package com.soundhaven.app.playlist.di

import com.soundhaven.app.playlist.search.data.ResourceProviderImpl
import com.soundhaven.app.playlist.search.data.api.ResourceProvider
import org.koin.dsl.module

val resourceModule = module{
    single<ResourceProvider> {
        ResourceProviderImpl(get())
    }
}