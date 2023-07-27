package com.example.playlistmaker.playlist.search.domain

import com.example.playlistmaker.playlist.search.domain.models.Track
import com.example.playlistmaker.playlist.util.Resource
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    fun searchTrack(expression:String): Flow<Resource<List<Track>>>
    fun getTrack():Flow<Array<Track>>
    fun writeSharedPrefsTrack(track: List<Track>)

}
