package com.example.playlistmaker.playlist.search.data

import android.content.SharedPreferences
import com.example.playlistmaker.playlist.search.data.dto.Response
import com.example.playlistmaker.playlist.search.data.dto.TrackDto
import com.example.playlistmaker.playlist.search.data.dto.TrackWriteResponce
import com.example.playlistmaker.playlist.search.domain.models.Track

interface TrackStorage {
    fun doRequest():Array<Track>
    fun doWrite(historyTrack: List<Track>)
}