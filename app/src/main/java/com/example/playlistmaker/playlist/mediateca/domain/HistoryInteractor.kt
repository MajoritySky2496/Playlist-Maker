package com.example.playlistmaker.playlist.mediateca.domain

import com.example.playlistmaker.playlist.search.data.dto.TrackDto
import com.example.playlistmaker.playlist.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface HistoryInteractor {
    suspend fun insertTrack(track: Track)
    suspend fun deleteTrack(track: Track)
    fun historyTrack(): Flow<Pair<List<Track>?, String?>>
}