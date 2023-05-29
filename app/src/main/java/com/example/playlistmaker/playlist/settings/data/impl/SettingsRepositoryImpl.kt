package com.example.playlistmaker.playlist.settings.data.impl

import android.content.SharedPreferences
import com.example.playlistmaker.playlist.settings.domain.api.SettingsRepository


class SettingsRepositoryImpl(private  val storage: SettingStorage) : SettingsRepository {
    override fun getThemeSettings(): Boolean {
       return storage.getThemeSettings()
    }

    override fun updateThemeSettings(darkTheme:Boolean) {
        storage.updateThemeSettings(darkTheme)
    }


}