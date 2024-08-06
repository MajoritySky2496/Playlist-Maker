package com.example.soundhaven.playlist.di

import com.example.soundhaven.playlist.mediateca.presentation.PlayListsViewModel
import com.example.soundhaven.playlist.mediateca.presentation.SelectedTracksViewModel
import com.example.soundhaven.playlist.player.presentation.PlayerViewModel
import com.example.soundhaven.playlist.playlist.presentation.viewmodel.AboutPlayListViewModel
import com.example.soundhaven.playlist.playlist.presentation.viewmodel.PlayListRedactorViewModel
import com.example.soundhaven.playlist.playlist.presentation.viewmodel.PlayListViewModel
import com.example.soundhaven.playlist.search.domain.models.Track
import com.example.soundhaven.playlist.search.presentation.TracksSearchViewModel
import com.example.soundhaven.playlist.settings.presentation.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {

    viewModel {
        TracksSearchViewModel(get(), get())
    }
    viewModel { (track: Track) ->
        PlayerViewModel(get(), get(), track, get(), get())
    }
    viewModel {
        SettingsViewModel(get(), get(), get())
    }
    viewModel {
        PlayListsViewModel(get())
    }
    viewModel {
        SelectedTracksViewModel(get(), get())
    }
    viewModel {
        PlayListViewModel(get(), get())
    }
    viewModel {
        AboutPlayListViewModel(get(), get(), get())
    }
    viewModel {
        PlayListRedactorViewModel(get(), get())
    }

}