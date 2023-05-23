package com.example.playlistmaker.playlist.settings.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.playlist.creator.Creator
import com.example.playlistmaker.playlist.settings.domain.SettingsInteractor
import com.example.playlistmaker.playlist.settings.ui.model.SwitcherState
import com.example.playlistmaker.playlist.sharing.domain.SharingInteractor

class SettingsViewModel(
    private val sharingInteractor: SharingInteractor,
    private val settingsInteractor: SettingsInteractor
):ViewModel() {

    private val switcherState = MutableLiveData<SwitcherState>()
    init {
        switchTheme(settingsInteractor.getThemeSettings())
        switcherToggle()


    }
    fun getSwitcherState(): LiveData<SwitcherState> = switcherState

    var darkTheme = false

    fun switchTheme(darkThemeEnable:Boolean){
        darkTheme  =darkThemeEnable
        settingsInteractor.updateThemeSettings(SWITCH_KEY, darkTheme)
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

    companion object{
        const val DARK_THEME = false
        const val SWITCH_KEY = "key_for_switch"
        fun getViewModelFactory(context: Context): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SettingsViewModel(
                    settingsInteractor = Creator.provideSettingInteractor(context),
                    sharingInteractor = Creator.provideSharingInteractor(context
                    )




                )

            }
        }

    }




}