package com.example.playlistmaker.playlist.search.domain

import com.example.playlistmaker.playlist.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface TrackSearchInteractor {

    fun searchTrack(expression:String): Flow<Pair<List<Track>?, String?>>
    fun getTrack():Flow<Array<Track>>
    fun writeTrack(track: List<Track>)
}