package com.example.playlistmaker.playlist.search.ui.tracks.models

import com.example.playlistmaker.playlist.search.domain.models.Track

sealed interface TrackSearchState{
    object Loading:TrackSearchState
    data class TrackContent(
        val tracks:List<Track>
    ):TrackSearchState
    data class HistroryContent(
        val historyTrack:List<Track>
    ):TrackSearchState
    data class Error(
        val errorMessage:String
    ):TrackSearchState
    data class Empty(
        val message: String
    ):TrackSearchState
}