package com.example.playlistmaker.playlist.search.domain.api

import com.example.playlistmaker.playlist.search.domain.models.Track
import com.example.playlistmaker.playlist.util.Resource

interface TracksRepository {
    fun searchTrack(expression:String): Resource<List<Track>>
}
