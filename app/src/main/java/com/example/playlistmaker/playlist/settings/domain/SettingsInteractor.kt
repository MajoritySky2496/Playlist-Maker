package com.example.playlistmaker.playlist.settings.domain



interface SettingsInteractor {
    fun getThemeSettings(): Boolean
    fun updateThemeSettings(key:String, theme:Boolean)

}