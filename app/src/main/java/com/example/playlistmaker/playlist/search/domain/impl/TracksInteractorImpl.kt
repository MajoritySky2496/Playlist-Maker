package com.example.playlistmaker.playlist.search.domain.impl

import com.example.playlistmaker.playlist.search.domain.api.TrackInteractor
import com.example.playlistmaker.playlist.search.domain.api.TracksRepository
import com.example.playlistmaker.playlist.util.Resource
import java.util.concurrent.Executors

class TracksInteractorImpl(private val repository: TracksRepository):TrackInteractor {
    private val executor = Executors.newCachedThreadPool()
    override fun searchTrack(expression: String, consumer: TrackInteractor.TrackConsumer) {
        executor.execute{
            when(val resourse = repository.searchTrack(expression)){
                is Resource.Success -> {consumer.consume(resourse.data, null)}

                is Resource.Error -> {consumer.consume(null, resourse.message)}
            }
        }
    }
}