package com.example.playlistmaker.playlist.di

import com.example.playlistmaker.playlist.domain.settingsDomain.impl.SettingsInteractorImpl
import com.example.playlistmaker.playlist.player.domain.api.PlayerInteractor
import com.example.playlistmaker.playlist.player.domain.impl.PlayerInteractorImpl
import com.example.playlistmaker.playlist.search.domain.api.TrackSearchInteractor
import com.example.playlistmaker.playlist.search.domain.impl.TracksSearchInteractorImpl
import com.example.playlistmaker.playlist.settings.domain.api.SettingsInteractor
import com.example.playlistmaker.playlist.sharing.domain.SharingInteractor
import com.example.playlistmaker.playlist.sharing.domain.impl.SharingInteractorImpl
import org.koin.dsl.module

val interactorModule = module{
    single<TrackSearchInteractor> {
        TracksSearchInteractorImpl(get(), get())
    }
    factory<PlayerInteractor> {PlayerInteractorImpl(get())  }
    single<SettingsInteractor> {SettingsInteractorImpl(get())  }
    single<SharingInteractor> {SharingInteractorImpl(get())  }

}