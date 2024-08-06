package com.example.soundhaven.playlist.settings.domain.api



interface SettingsInteractor {
    fun getThemeSettings(): Boolean
    fun updateThemeSettings(darkTheme:Boolean)

}