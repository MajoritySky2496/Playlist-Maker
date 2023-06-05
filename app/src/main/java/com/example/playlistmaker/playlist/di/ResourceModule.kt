package com.example.playlistmaker.playlist.di

import com.example.playlistmaker.playlist.search.data.ResourceProviderImpl
import com.example.playlistmaker.playlist.search.domain.api.ResourceProvider
import org.koin.dsl.module

val resourceModule = module{
    single<ResourceProvider> {
        ResourceProviderImpl(get())
    }
}