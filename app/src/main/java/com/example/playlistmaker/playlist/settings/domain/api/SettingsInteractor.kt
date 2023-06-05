package com.example.playlistmaker.playlist.settings.domain.api



interface SettingsInteractor {
    fun getThemeSettings(): Boolean
    fun updateThemeSettings(darkTheme:Boolean)

}