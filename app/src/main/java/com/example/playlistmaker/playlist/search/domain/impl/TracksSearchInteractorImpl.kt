package com.example.playlistmaker.playlist.search.domain.impl

import com.example.playlistmaker.playlist.search.domain.api.TrackSearchInteractor
import com.example.playlistmaker.playlist.search.domain.api.TracksRepository
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.example.playlistmaker.playlist.util.Resource
import java.util.concurrent.ExecutorService

class TracksSearchInteractorImpl(private val repository: TracksRepository, private val executor: ExecutorService):TrackSearchInteractor {

    override fun searchTrack(expression: String, consumer: TrackSearchInteractor.TrackConsumer) {
        executor.execute{
            when(val resourse = repository.searchTrack(expression)){
                is Resource.Success -> {consumer.consume(resourse.data, null)}

                is Resource.Error -> {consumer.consume(null, resourse.message)}
            }
        }
    }

    override fun getTrack(): Array<Track> {
        return repository.getTrack()

    }

    override fun writeTrack(track: List<Track>) {
        repository.writeSharedPrefsTrack(track)
    }
}