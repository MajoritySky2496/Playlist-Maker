package com.example.playlistmaker.playlist.search.data


import com.example.playlistmaker.playlist.search.data.dto.TrackDto


interface TrackStorage {
    fun doRequest():Array<TrackDto>
    fun doWrite(historyTrack: List<TrackDto>)
}