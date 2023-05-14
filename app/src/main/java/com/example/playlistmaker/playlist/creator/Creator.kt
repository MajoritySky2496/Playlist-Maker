package com.example.playlistmaker.playlist.creator

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.App
import com.example.playlistmaker.playlist.search.data.TrackRepositoryImpl
import com.example.playlistmaker.playlist.search.data.localwork.SharedPrefsStorage
import com.example.playlistmaker.playlist.search.data.network.RetrofitNetworkClient
import com.example.playlistmaker.playlist.search.domain.api.TrackSearchInteractor
import com.example.playlistmaker.playlist.search.domain.api.TracksRepository
import com.example.playlistmaker.playlist.search.domain.impl.TracksSearchInteractorImpl

object Creator {
    private fun getTracksRepository(context:Context, sharedPrefs: SharedPreferences): TracksRepository{
        return TrackRepositoryImpl(RetrofitNetworkClient(context), SharedPrefsStorage(sharedPrefs) )
    }
    fun provideTracksInteractor(context: Context): TrackSearchInteractor {
        return TracksSearchInteractorImpl(getTracksRepository(context, getSharedPreference(context) ))
    }
    private fun getSharedPreference(context: Context):SharedPreferences{
        return context.getSharedPreferences(SharedPrefsStorage.PRACTICUM_EXAMPLE_PREFERENCES,
            AppCompatActivity.MODE_PRIVATE
        )
    }



//        fun provideTracksSearchControler(activity: Activity, adapter: TrackAdapter, sharedPrefs:SharedPreferences): TracksSearchViewModel {
//            return TracksSearchViewModel(activity, adapter, sharedPrefs)
//        }
    }
