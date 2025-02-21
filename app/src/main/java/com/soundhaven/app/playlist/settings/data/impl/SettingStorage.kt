package com.soundhaven.app.playlist.settings.data.impl

interface SettingStorage {
    fun getThemeSettings(): Boolean
    fun updateThemeSettings(darkTheme:Boolean)
}