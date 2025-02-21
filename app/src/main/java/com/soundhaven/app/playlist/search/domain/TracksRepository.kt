package com.soundhaven.app.playlist.search.domain

import com.soundhaven.app.playlist.search.domain.models.Track
import com.soundhaven.app.playlist.util.Resource
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    fun searchTrack(expression:String): Flow<Resource<List<Track>>>
    fun getTrack():Flow<Array<Track>>
    fun writeSharedPrefsTrack(track: List<Track>)

}
