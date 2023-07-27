package com.example.playlistmaker.playlist.di

import com.example.playlistmaker.playlist.mediateca.data.HistoryRepositoryImpl
import com.example.playlistmaker.playlist.mediateca.data.converters.TrackDbConvertor
import com.example.playlistmaker.playlist.mediateca.domain.HistoryRepository
import com.example.playlistmaker.playlist.player.data.TracksMediaPlayer
import com.example.playlistmaker.playlist.player.domain.api.MediaPlayerRepository
import com.example.playlistmaker.playlist.search.data.TrackRepositoryImpl
import com.example.playlistmaker.playlist.search.domain.TracksRepository
import com.example.playlistmaker.playlist.settings.data.impl.SettingsRepositoryImpl
import com.example.playlistmaker.playlist.settings.domain.api.SettingsRepository
import org.koin.dsl.module
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

val repositoryModule = module{

    single<ExecutorService> {
        Executors.newCachedThreadPool()
    }
    single<TracksRepository>{
        TrackRepositoryImpl(get(), get(), get(), get(), get())
    }
    factory<MediaPlayerRepository> {
        TracksMediaPlayer()
    }
    single<SettingsRepository> {
        SettingsRepositoryImpl(get())
    }
    single<HistoryRepository> {HistoryRepositoryImpl(get(), get(), get())  }
    factory { TrackDbConvertor() }
}
