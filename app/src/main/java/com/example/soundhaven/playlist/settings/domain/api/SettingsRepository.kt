package com.example.soundhaven.playlist.settings.domain.api



interface SettingsRepository {
    fun getThemeSettings():Boolean
    fun updateThemeSettings(darkTheme:Boolean)
}