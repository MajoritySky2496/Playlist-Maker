package com.example.soundhaven.playlist.mediateca.domain

import com.example.soundhaven.playlist.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface HistoryInteractor {
    suspend fun insertTrack(track: Track)
    suspend fun deleteTrack(track: Track)
    fun historyTrack(): Flow<Pair<List<Track>?, String?>>
}