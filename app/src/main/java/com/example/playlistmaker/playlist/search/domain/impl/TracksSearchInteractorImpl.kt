package com.example.playlistmaker.playlist.search.domain.impl

import com.example.playlistmaker.playlist.search.domain.TrackSearchInteractor
import com.example.playlistmaker.playlist.search.domain.TracksRepository
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.example.playlistmaker.playlist.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TracksSearchInteractorImpl(private val repository: TracksRepository): TrackSearchInteractor {

    override fun searchTrack(expression: String): Flow<Pair<List<Track>?, String?>> {
        return repository.searchTrack(expression).map { result ->
            when(result){
                is Resource.Success ->{
                    Pair(result.data, null)
                }
                is Resource.Error ->{
                    Pair(null, result.message)
                }
            }
        }
    }

    override  fun getTrack(): Flow<Array<Track>> {
        return repository.getTrack()

    }

    override fun writeTrack(track: List<Track>) {
        repository.writeSharedPrefsTrack(track)
    }
}