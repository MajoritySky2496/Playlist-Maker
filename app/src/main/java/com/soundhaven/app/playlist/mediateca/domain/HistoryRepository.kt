package com.soundhaven.app.playlist.mediateca.domain

import com.soundhaven.app.playlist.search.domain.models.Track
import com.soundhaven.app.playlist.util.Resource
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
     suspend fun insertTrack(track: Track)
     suspend fun deleteTrack(track: Track)
    fun historyTrack(): Flow<Resource<List<Track>?>>
}