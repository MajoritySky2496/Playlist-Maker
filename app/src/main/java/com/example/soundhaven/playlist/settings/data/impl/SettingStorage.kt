package com.example.soundhaven.playlist.settings.data.impl

interface SettingStorage {
    fun getThemeSettings(): Boolean
    fun updateThemeSettings(darkTheme:Boolean)
}