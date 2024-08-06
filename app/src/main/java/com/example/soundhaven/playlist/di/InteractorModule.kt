package com.example.soundhaven.playlist.di

import com.example.soundhaven.playlist.domain.settingsDomain.impl.SettingsInteractorImpl
import com.example.soundhaven.playlist.mediateca.domain.HistoryInteractor
import com.example.soundhaven.playlist.mediateca.domain.Impl.HistoryInteractorImpl
import com.example.soundhaven.playlist.player.domain.api.PlayerInteractor
import com.example.soundhaven.playlist.player.domain.impl.PlayerInteractorImpl
import com.example.soundhaven.playlist.playlist.domain.PlayListInteractor
import com.example.soundhaven.playlist.playlist.domain.impl.PlayListInteractorImpl
import com.example.soundhaven.playlist.search.domain.TrackSearchInteractor
import com.example.soundhaven.playlist.search.domain.impl.TracksSearchInteractorImpl
import com.example.soundhaven.playlist.settings.domain.api.SettingsInteractor
import com.example.soundhaven.playlist.sharing.domain.SharingInteractor
import com.example.soundhaven.playlist.sharing.domain.impl.SharingInteractorImpl
import org.koin.dsl.module

val interactorModule = module{
    single<TrackSearchInteractor> {
        TracksSearchInteractorImpl(get())
    }
    factory<PlayerInteractor> { PlayerInteractorImpl(get(), get()) }
    single<SettingsInteractor> {SettingsInteractorImpl(get())}
    single<SharingInteractor> {SharingInteractorImpl(get())  }
    single<HistoryInteractor> {HistoryInteractorImpl(get())  }
    single<PlayListInteractor> {PlayListInteractorImpl(get(), get(), get())  }

}