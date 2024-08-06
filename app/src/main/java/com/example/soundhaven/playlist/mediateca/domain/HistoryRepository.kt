package com.example.soundhaven.playlist.mediateca.domain

import com.example.soundhaven.playlist.search.domain.models.Track
import com.example.soundhaven.playlist.util.Resource
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
     suspend fun insertTrack(track: Track)
     suspend fun deleteTrack(track: Track)
    fun historyTrack(): Flow<Resource<List<Track>?>>
}