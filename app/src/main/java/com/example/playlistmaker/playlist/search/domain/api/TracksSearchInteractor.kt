package com.example.playlistmaker.playlist.search.domain.api

import android.content.SharedPreferences
import com.example.playlistmaker.playlist.search.domain.models.Track

interface TrackSearchInteractor {

    fun searchTrack(expression:String, consumer:TrackConsumer)
    fun getTrack():Array<Track>
    fun writeTrack(track: List<Track>)


    interface TrackConsumer{
        fun consume(foundTracks:List<Track>?, errorMessage:String?)
    }

}