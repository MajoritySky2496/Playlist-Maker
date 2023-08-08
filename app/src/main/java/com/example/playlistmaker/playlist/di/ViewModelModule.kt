package com.example.playlistmaker.playlist.di

import com.example.playlistmaker.playlist.mediateca.presentation.PlayListsViewModel
import com.example.playlistmaker.playlist.mediateca.presentation.SelectedTracksViewModel
import com.example.playlistmaker.playlist.player.presentation.PlayerViewModel
import com.example.playlistmaker.playlist.playlist.presentation.PlayListViewModel
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.example.playlistmaker.playlist.search.presentation.TracksSearchViewModel
import com.example.playlistmaker.playlist.settings.presentation.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {

    viewModel{
        TracksSearchViewModel(get(), get())
}
    viewModel{(track:Track)->
        PlayerViewModel(get(), get(), track, get(), get())
    }
    viewModel{
        SettingsViewModel(get(), get())
    }
    viewModel{
        PlayListsViewModel(get())
    }
    viewModel{
        SelectedTracksViewModel(get(), get())
    }
    viewModel{
        PlayListViewModel(get(), get())
    }

}