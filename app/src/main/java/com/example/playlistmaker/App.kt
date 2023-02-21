package com.example.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
const val DARK_THEME = false
const val SWITCH_KEY = "key_for_switch"

class App:Application() {

    var darkTheme = false
    override fun onCreate() {
        super.onCreate()
        switchTheme(LoadPreference())









    }
    fun switchTheme(darkThemeEnable:Boolean){
        darkTheme  =darkThemeEnable
        SavedPreference(SWITCH_KEY, darkTheme)

        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnable) {
                AppCompatDelegate.MODE_NIGHT_YES
            }else{
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
    fun SavedPreference(key:String, theme:Boolean){
        var sharedPrefs = getSharedPreferences(DARK_THEME.toString(), MODE_PRIVATE)
        sharedPrefs.edit().putBoolean(key, theme).apply()
    }
    fun LoadPreference():Boolean{
        var sharedPrefs = getSharedPreferences(DARK_THEME.toString(), MODE_PRIVATE)
        return  sharedPrefs.getBoolean(SWITCH_KEY, DARK_THEME)

    }
}