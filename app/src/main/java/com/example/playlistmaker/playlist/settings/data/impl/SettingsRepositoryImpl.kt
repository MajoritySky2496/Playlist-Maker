package com.example.playlistmaker.playlist.settings.data.impl

import android.content.SharedPreferences
import com.example.playlistmaker.playlist.settings.domain.api.SettingsRepository
import com.example.playlistmaker.playlist.settings.presentation.SettingsViewModel.Companion.DARK_THEME
import com.example.playlistmaker.playlist.settings.presentation.SettingsViewModel.Companion.SWITCH_KEY

class SettingsRepositoryImpl(private val sharedPrefs: SharedPreferences) : SettingsRepository {
    override fun getThemeSettings(): Boolean {
        return sharedPrefs.getBoolean(
            SWITCH_KEY,
            DARK_THEME
        )
    }

    override fun updateThemeSettings(key: String, theme: Boolean) {
        sharedPrefs.edit().putBoolean(key, theme).apply()
    }


}