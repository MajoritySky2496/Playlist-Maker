package com.example.soundhaven.playlist.settings.data.impl

import com.example.soundhaven.playlist.settings.domain.api.SettingsRepository


class SettingsRepositoryImpl(private  val storage: SettingStorage) : SettingsRepository {
    override fun getThemeSettings(): Boolean {
       return storage.getThemeSettings()
    }

    override fun updateThemeSettings(darkTheme:Boolean) {
        storage.updateThemeSettings(darkTheme)
    }


}