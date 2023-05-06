package com.example.playlistmaker.playlist.ui.settings.view_model

import androidx.lifecycle.ViewModel
import com.example.playlistmaker.playlist.settings.domain.SettingsInteractor
import com.example.playlistmaker.playlist.sharing.domain.SharingInteractor

class SettingsViewModel(
    private val sharingInteractor: SharingInteractor,
    private val settingsInteractor: SettingsInteractor,
):ViewModel() {

}