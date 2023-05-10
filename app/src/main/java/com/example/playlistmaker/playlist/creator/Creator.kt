package com.example.playlistmaker.playlist.creator

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.playlistmaker.playlist.search.data.TrackRepositoryImpl
import com.example.playlistmaker.playlist.search.data.localwork.SharedPrefsStorage
import com.example.playlistmaker.playlist.search.data.network.RetrofitNetworkClient
import com.example.playlistmaker.playlist.search.domain.api.TrackInteractor
import com.example.playlistmaker.playlist.search.domain.api.TracksRepository
import com.example.playlistmaker.playlist.search.domain.impl.TracksInteractorImpl
import com.example.playlistmaker.playlist.search.ui.tracks.SearchActivity
import com.example.playlistmaker.playlist.search.ui.tracks.TrackAdapter
import com.example.playlistmaker.playlist.search.ui.tracks.TracksSearchController

object Creator {
    private fun getTracksRepository(context:Context, sharedPrefs: SharedPreferences): TracksRepository{
        return TrackRepositoryImpl(RetrofitNetworkClient(context), SharedPrefsStorage(sharedPrefs) )
    }
    fun provideTracksInteractor(context: Context, sharedPrefs: SharedPreferences): TrackInteractor {
        return TracksInteractorImpl(getTracksRepository(context, sharedPrefs ))
    }

        fun provideTracksSearchControler(activity: Activity, adapter: TrackAdapter, sharedPrefs:SharedPreferences):TracksSearchController{
            return TracksSearchController(activity, adapter, sharedPrefs)
        }
    }
