package com.example.playlistmaker.playlist.creator

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

import com.example.playlistmaker.playlist.domain.settingsDomain.impl.SettingsInteractorImpl
import com.example.playlistmaker.playlist.player.data.TracksMediaPlayer
import com.example.playlistmaker.playlist.player.domain.api.IPlayerInteractor
import com.example.playlistmaker.playlist.player.domain.api.MediaPlayerRepository
import com.example.playlistmaker.playlist.player.domain.impl.PlayerInteractor
import com.example.playlistmaker.playlist.search.data.TrackRepositoryImpl
import com.example.playlistmaker.playlist.search.data.localwork.SharedPrefsStorage
import com.example.playlistmaker.playlist.search.data.network.RetrofitNetworkClient
import com.example.playlistmaker.playlist.search.domain.api.TrackSearchInteractor
import com.example.playlistmaker.playlist.search.domain.api.TracksRepository
import com.example.playlistmaker.playlist.search.domain.impl.TracksSearchInteractorImpl
import com.example.playlistmaker.playlist.settings.domain.api.SettingsRepository
import com.example.playlistmaker.playlist.settings.data.impl.SettingsRepositoryImpl
import com.example.playlistmaker.playlist.settings.domain.SettingsInteractor
import com.example.playlistmaker.playlist.settings.presentation.SettingsViewModel.Companion.DARK_THEME
import com.example.playlistmaker.playlist.sharing.data.ExternalNavigator
import com.example.playlistmaker.playlist.sharing.data.impl.ExternalNavigatorImpl
import com.example.playlistmaker.playlist.sharing.domain.SharingInteractor
import com.example.playlistmaker.playlist.sharing.domain.impl.SharingInteractorImpl

object Creator {
    private fun getTracksRepository(context:Context, sharedPrefs: SharedPreferences): TracksRepository{
        return TrackRepositoryImpl(RetrofitNetworkClient(context), SharedPrefsStorage(sharedPrefs) )
    }
    fun provideTracksInteractor(context: Context): TrackSearchInteractor {
        return TracksSearchInteractorImpl(getTracksRepository(context, getSharedPreference(context, SharedPrefsStorage.PRACTICUM_EXAMPLE_PREFERENCES) ))
    }
    fun getSettingsRepository(sharedPrefs: SharedPreferences): SettingsRepository {
        return SettingsRepositoryImpl(sharedPrefs)
    }
    fun provideSettingInteractor(context: Context):SettingsInteractor{
        return SettingsInteractorImpl(getSettingsRepository(getSharedPreference(context, DARK_THEME.toString())))
    }
    fun provideSharingInteractor(context: Context):SharingInteractor{
        return SharingInteractorImpl(getExternalNavigator(context))
    }
    fun getExternalNavigator(context: Context):ExternalNavigator{
        return ExternalNavigatorImpl(context)
    }
    private fun getSharedPreference(context: Context, key:String):SharedPreferences{
        return context.getSharedPreferences(key,
            AppCompatActivity.MODE_PRIVATE
        )
    }

     fun providePlayerInteractor(): IPlayerInteractor {
        return PlayerInteractor(
            player = getAudioPlayer()
        )
    }
    private fun getAudioPlayer():MediaPlayerRepository{
        return TracksMediaPlayer()
    }

    }
