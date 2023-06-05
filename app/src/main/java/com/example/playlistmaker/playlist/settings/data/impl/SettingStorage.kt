package com.example.playlistmaker.playlist.settings.data.impl

interface SettingStorage {
    fun getThemeSettings(): Boolean
    fun updateThemeSettings(darkTheme:Boolean)
}