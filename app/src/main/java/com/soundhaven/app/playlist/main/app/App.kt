package com.soundhaven.app.playlist.main.app

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.soundhaven.app.playlist.di.dagger.AppComponent
import com.soundhaven.app.playlist.di.dagger.DaggerAppComponent

class App : Application() {

    var darkTheme = false
    lateinit var appComponent: AppComponent


    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(com.soundhaven.app.playlist.di.dagger.AppModule(this)) // Передаём Application
            .build()
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}

val Context.appComponent: AppComponent
    get() = (applicationContext as App).appComponent