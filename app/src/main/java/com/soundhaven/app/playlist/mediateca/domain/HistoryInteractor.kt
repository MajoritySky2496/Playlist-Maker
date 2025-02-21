package com.soundhaven.app.playlist.mediateca.domain

import com.soundhaven.app.playlist.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface HistoryInteractor {
    suspend fun insertTrack(track: Track)
    suspend fun deleteTrack(track: Track)
    fun historyTrack(): Flow<Pair<List<Track>?, String?>>
}