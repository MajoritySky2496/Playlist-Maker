package com.soundhaven.app.playlist.di

import com.soundhaven.app.playlist.mediateca.presentation.PlayListsViewModel
import com.soundhaven.app.playlist.mediateca.presentation.SelectedTracksViewModel
import com.soundhaven.app.playlist.player.presentation.PlayerViewModel
import com.soundhaven.app.playlist.playlist.presentation.viewmodel.AboutPlayListViewModel
import com.soundhaven.app.playlist.playlist.presentation.viewmodel.PlayListRedactorViewModel
import com.soundhaven.app.playlist.playlist.presentation.viewmodel.PlayListViewModel
import com.soundhaven.app.playlist.search.domain.models.Track
import com.soundhaven.app.playlist.search.presentation.TracksSearchViewModel
import com.soundhaven.app.playlist.settings.presentation.SettingsViewModel
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