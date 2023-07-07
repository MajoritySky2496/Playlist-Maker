package com.example.playlistmaker.playlist.mediateca.domain.Impl

import com.example.playlistmaker.playlist.mediateca.domain.HistoryInteractor
import com.example.playlistmaker.playlist.mediateca.domain.HistoryRepository
import com.example.playlistmaker.playlist.search.data.dto.TrackDto
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.example.playlistmaker.playlist.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class HistoryInteractorImpl(private val historyRepository: HistoryRepository):HistoryInteractor {
    override suspend fun insertTrack(track: Track){
       historyRepository.insertTrack(track)
    }

    override suspend fun deleteTrack(track: Track) {
        historyRepository.deleteTrack(track)
    }

    override fun historyTrack(): Flow<Pair<List<Track>?, String?>> {
        return historyRepository.historyTrack().map { result ->

            when(result){
                is Resource.Success -> {
                    Pair(result.data, null)
                }
                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }

    }
}