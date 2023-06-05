package com.example.playlistmaker.playlist.search.domain.api

import com.example.playlistmaker.playlist.search.domain.models.Track

interface TrackSearchInteractor {

    fun searchTrack(expression:String, consumer:TrackConsumer)
    fun getTrack():Array<Track>
    fun writeTrack(track: List<Track>)


    interface TrackConsumer{
        fun consume(foundTracks:List<Track>?, errorMessage:String?)
    }

}