package com.example.playlistmaker.playlist.search.domain.api

import com.example.playlistmaker.playlist.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface TrackSearchInteractor {

    fun searchTrack(expression:String): Flow<Pair<List<Track>?, String?>>
    fun getTrack():Array<Track>
    fun writeTrack(track: List<Track>)


    interface TrackConsumer{
        fun consume(foundTracks:List<Track>?, errorMessage:String?)
    }

}