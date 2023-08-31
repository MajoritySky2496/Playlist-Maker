package com.example.playlistmaker.playlist.settings.presentation

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.search.data.api.ResourceProvider
import com.example.playlistmaker.playlist.settings.domain.api.SettingsInteractor
import com.example.playlistmaker.playlist.settings.ui.model.SwitcherState
import com.example.playlistmaker.playlist.sharing.domain.SharingInteractor
import com.example.playlistmaker.playlist.sharing.domain.impl.SharingInteractorImpl.Companion.LINK_YANDEX

class SettingsViewModel(

    private val settingsInteractor: SettingsInteractor,
    private val sharingInteractor: SharingInteractor,
    private val resourceProvider: ResourceProvider


):ViewModel() {
    private val subject1 = resourceProvider.getString(R.string.subject_1)
    private val subject2 = resourceProvider.getString(R.string.subject_2)

    init {
        switchTheme(settingsInteractor.getThemeSettings())
        switcherToggle()
    }
    fun switchTheme(darkThemeEnable:Boolean){

        settingsInteractor.updateThemeSettings(darkThemeEnable)
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnable) {
                AppCompatDelegate.MODE_NIGHT_YES
            }else{
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
    fun getThemeSettings():Boolean{
      return  settingsInteractor.getThemeSettings()
    }
    fun switcherToggle(){
        SwitcherState.toggle(settingsInteractor.getThemeSettings())

    }
    fun shareApp(){
        sharingInteractor.shareApp(LINK_YANDEX)
    }
    fun openSupport(){
        sharingInteractor.openSupport(subject1, subject2)
    }
    fun  openTerms(){
        sharingInteractor.openTerms()
    }



}