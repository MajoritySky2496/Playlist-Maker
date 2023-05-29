package com.example.playlistmaker.playlist.main.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

import com.example.playlistmaker.playlist.di.dataModule
import com.example.playlistmaker.playlist.di.interactorModule
import com.example.playlistmaker.playlist.di.repositoryModule
import com.example.playlistmaker.playlist.di.resourceModule
import com.example.playlistmaker.playlist.di.viewModelModule
import com.example.playlistmaker.playlist.settings.domain.api.SettingsInteractor
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext

import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf

class App:Application() {

    var darkTheme = false
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@App)
            modules(dataModule, repositoryModule, interactorModule, viewModelModule, resourceModule)
        }
        val settingInteractor: SettingsInteractor by inject { parametersOf(this) }


        switchTheme(settingInteractor.getThemeSettings())
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}