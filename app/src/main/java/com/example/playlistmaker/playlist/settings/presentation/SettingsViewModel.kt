package com.example.playlistmaker.playlist.settings.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

import com.example.playlistmaker.playlist.settings.domain.api.SettingsInteractor
import com.example.playlistmaker.playlist.settings.ui.model.SwitcherState
import com.example.playlistmaker.playlist.sharing.domain.SharingInteractor

class SettingsViewModel(

    private val settingsInteractor: SettingsInteractor,
    private val sharingInteractor: SharingInteractor

):ViewModel() {



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
        sharingInteractor.shareApp()
    }
    fun openSupport(){
        sharingInteractor.openSupport()
    }
    fun  openTerms(){
        sharingInteractor.openTerms()
    }


}