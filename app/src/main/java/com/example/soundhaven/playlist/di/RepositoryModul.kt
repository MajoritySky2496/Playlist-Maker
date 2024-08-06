package com.example.soundhaven.playlist.di

import com.example.soundhaven.playlist.mediateca.data.HistoryRepositoryImpl
import com.example.soundhaven.playlist.mediateca.data.converters.TrackDbConvertor
import com.example.soundhaven.playlist.mediateca.domain.HistoryRepository
import com.example.soundhaven.playlist.player.data.PlayerRepositoryImpl
import com.example.soundhaven.playlist.player.data.TracksMediaPlayer
import com.example.soundhaven.playlist.player.domain.PlayerRepository
import com.example.soundhaven.playlist.player.domain.api.MediaPlayerRepository
import com.example.soundhaven.playlist.playlist.data.PlayListRepositoryImpl
import com.example.soundhaven.playlist.playlist.data.converters.PlayListDbConvertor
import com.example.soundhaven.playlist.playlist.domain.PlayListRepository
import com.example.soundhaven.playlist.search.data.TrackRepositoryImpl
import com.example.soundhaven.playlist.search.domain.TracksRepository
import com.example.soundhaven.playlist.settings.data.impl.SettingsRepositoryImpl
import com.example.soundhaven.playlist.settings.domain.api.SettingsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

val repositoryModule = module{

    single<ExecutorService> {
        Executors.newCachedThreadPool()
    }
    single<TracksRepository>{
        TrackRepositoryImpl(get(), get(), get(), get(), )
    }
    factory<MediaPlayerRepository> {
        TracksMediaPlayer()
    }
    single<SettingsRepository> {
        SettingsRepositoryImpl(get())
    }
    single<HistoryRepository> {HistoryRepositoryImpl(get(), get(), get())  }
    factory { TrackDbConvertor() }

    single<PlayListRepository>{PlayListRepositoryImpl(get(),get(), get(), get())}
    factory { PlayListDbConvertor() }

    singleOf(::PlayerRepositoryImpl).bind<PlayerRepository>()
}
