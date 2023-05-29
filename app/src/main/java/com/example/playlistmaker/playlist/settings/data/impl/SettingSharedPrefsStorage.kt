package com.example.playlistmaker.playlist.settings.data.impl

import android.content.SharedPreferences
import com.example.playlistmaker.playlist.search.data.TrackStorage
import com.example.playlistmaker.playlist.settings.data.impl.SettingSharedPrefsStorage.Companion.SWITCH_KEY
import com.example.playlistmaker.playlist.settings.presentation.SettingsViewModel


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