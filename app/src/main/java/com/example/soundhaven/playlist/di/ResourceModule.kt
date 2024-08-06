package com.example.soundhaven.playlist.di

import com.example.soundhaven.playlist.search.data.ResourceProviderImpl
import com.example.soundhaven.playlist.search.data.api.ResourceProvider
import org.koin.dsl.module

val resourceModule = module{
    single<ResourceProvider> {
        ResourceProviderImpl(get())
    }
}