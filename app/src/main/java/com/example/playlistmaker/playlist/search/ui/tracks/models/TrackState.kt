package com.example.playlistmaker.playlist.search.ui.tracks.models

import android.os.Message
import com.example.playlistmaker.playlist.search.domain.models.Track

sealed interface TrackState{
    object Loading:TrackState
    data class TrackContent(
        val tracks:List<Track>
    ):TrackState
    data class HistroryContent(
        val historyTrack:List<Track>
    ):TrackState
    data class Error(
        val errorMessage:String
    ):TrackState
    data class Empty(
        val message: String
    ):TrackState
}