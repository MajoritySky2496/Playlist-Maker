package com.soundhaven.app.playlist.di

import com.soundhaven.app.playlist.mediateca.data.HistoryRepositoryImpl
import com.soundhaven.app.playlist.mediateca.data.converters.TrackDbConvertor
import com.soundhaven.app.playlist.mediateca.domain.HistoryRepository
import com.soundhaven.app.playlist.player.data.PlayerRepositoryImpl
import com.soundhaven.app.playlist.player.data.TracksMediaPlayer
import com.soundhaven.app.playlist.player.domain.PlayerRepository
import com.soundhaven.app.playlist.player.domain.api.MediaPlayerRepository
import com.soundhaven.app.playlist.playlist.data.PlayListRepositoryImpl
import com.soundhaven.app.playlist.playlist.data.converters.PlayListDbConvertor
import com.soundhaven.app.playlist.playlist.domain.PlayListRepository
import com.soundhaven.app.playlist.search.data.TrackRepositoryImpl
import com.soundhaven.app.playlist.search.domain.TracksRepository
import com.soundhaven.app.playlist.settings.data.impl.SettingsRepositoryImpl
import com.soundhaven.app.playlist.settings.domain.api.SettingsRepository
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
