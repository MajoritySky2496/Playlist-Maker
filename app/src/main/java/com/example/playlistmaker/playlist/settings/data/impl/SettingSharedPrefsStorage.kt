package com.example.playlistmaker.playlist.settings.data.impl

import android.content.SharedPreferences


class SettingSharedPrefsStorage(val sharedPrefrs: SharedPreferences):SettingStorage {
    override fun getThemeSettings(): Boolean {
        return sharedPrefrs.getBoolean(

            SWITCH_KEY,
            DARK_THEME
        )
    }

    override fun updateThemeSettings( darckTheme:Boolean) {
        sharedPrefrs.edit().putBoolean(SWITCH_KEY, darckTheme).apply()
    }
    companion object{
        const val DARK_THEME = false
        const val SWITCH_KEY = "key_for_switch"
    }
}