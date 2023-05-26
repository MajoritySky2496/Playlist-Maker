package com.example.playlistmaker.playlist.settings.domain.api



interface SettingsRepository {
    fun getThemeSettings():Boolean
    fun updateThemeSettings(key:String, theme:Boolean)
}