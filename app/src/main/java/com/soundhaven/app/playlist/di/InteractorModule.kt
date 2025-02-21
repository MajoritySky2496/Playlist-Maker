package com.soundhaven.app.playlist.di

import com.soundhaven.app.playlist.domain.settingsDomain.impl.SettingsInteractorImpl
import com.soundhaven.app.playlist.mediateca.domain.HistoryInteractor
import com.soundhaven.app.playlist.mediateca.domain.Impl.HistoryInteractorImpl
import com.soundhaven.app.playlist.player.domain.api.PlayerInteractor
import com.soundhaven.app.playlist.player.domain.impl.PlayerInteractorImpl
import com.soundhaven.app.playlist.playlist.domain.PlayListInteractor
import com.soundhaven.app.playlist.playlist.domain.impl.PlayListInteractorImpl
import com.soundhaven.app.playlist.search.domain.TrackSearchInteractor
import com.soundhaven.app.playlist.search.domain.impl.TracksSearchInteractorImpl
import com.soundhaven.app.playlist.settings.domain.api.SettingsInteractor
import com.soundhaven.app.playlist.sharing.domain.SharingInteractor
import com.soundhaven.app.playlist.sharing.domain.impl.SharingInteractorImpl
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