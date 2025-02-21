package com.soundhaven.app.playlist.search.data


import com.soundhaven.app.playlist.search.data.dto.TrackDto


interface TrackStorage {
    fun doRequest():Array<TrackDto>
    fun doWrite(historyTrack: List<TrackDto>)
}