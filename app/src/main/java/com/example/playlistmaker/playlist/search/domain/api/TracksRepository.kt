package com.example.playlistmaker.playlist.search.domain.api

import android.content.SharedPreferences
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.example.playlistmaker.playlist.util.Resource

interface TracksRepository {
    fun searchTrack(expression:String) :Resource<List<Track>>
    fun getTrack():Array<Track>
    fun writeSharedPrefsTrack(track: List<Track>)

}
