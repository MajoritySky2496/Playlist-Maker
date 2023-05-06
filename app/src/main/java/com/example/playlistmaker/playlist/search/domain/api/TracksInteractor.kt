package com.example.playlistmaker.playlist.search.domain.api

import com.example.playlistmaker.playlist.search.domain.models.Track

interface TrackInteractor {

    fun searchTrack(expression:String, consumer:TrackConsumer)

    interface TrackConsumer{
        fun consume(foundTracks:List<Track>?, errorMessage:String?)
    }

}