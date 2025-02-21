package com.soundhaven.app.playlist.domain.settingsDomain.impl

import com.soundhaven.app.playlist.settings.domain.api.SettingsInteractor
import com.soundhaven.app.playlist.settings.domain.api.SettingsRepository

class SettingsInteractorImpl(private val prefs: SettingsRepository): SettingsInteractor {
    override fun getThemeSettings(): Boolean {
        return prefs.getThemeSettings()
    }

    override fun updateThemeSettings(darkTheme:Boolean) {
        prefs.updateThemeSettings(darkTheme)
    }

}