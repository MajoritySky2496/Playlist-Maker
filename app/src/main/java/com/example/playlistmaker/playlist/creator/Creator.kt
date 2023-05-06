package com.example.playlistmaker.playlist.creator

import android.app.Activity
import android.content.Context
import com.example.playlistmaker.playlist.search.data.TrackRepositoryImpl
import com.example.playlistmaker.playlist.search.data.network.RetrofitNetworkClient
import com.example.playlistmaker.playlist.search.domain.api.TrackInteractor
import com.example.playlistmaker.playlist.search.domain.api.TracksRepository
import com.example.playlistmaker.playlist.search.domain.impl.TracksInteractorImpl
import com.example.playlistmaker.playlist.search.ui.tracks.SearchActivity
import com.example.playlistmaker.playlist.search.ui.tracks.TrackAdapter
import com.example.playlistmaker.playlist.search.ui.tracks.TracksSearchController

object Creator {
    private fun getTracksRepository(context:Context): TracksRepository{
        return TrackRepositoryImpl(RetrofitNetworkClient(context))
    }
    fun provideTracksInteractor(context: Context): TrackInteractor {
        return TracksInteractorImpl(getTracksRepository(context))
    }

        fun provideTracksSearchControler(activity: Activity, adapter: TrackAdapter):TracksSearchController{
            return TracksSearchController(activity, adapter)
        }
    }
