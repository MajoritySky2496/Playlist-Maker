package com.example.soundhaven.playlist.search.data


import com.example.soundhaven.playlist.search.data.dto.TrackDto


interface TrackStorage {
    fun doRequest():Array<TrackDto>
    fun doWrite(historyTrack: List<TrackDto>)
}