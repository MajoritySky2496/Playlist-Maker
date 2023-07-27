package com.example.playlistmaker.playlist.mediateca.presentation.model

import com.example.playlistmaker.playlist.search.domain.models.Track

sealed interface SelectedTrackState {
    data class TrackContent(
        val tracks:List<Track>
    ): SelectedTrackState
    data class Error(
        val errorMessage:String
    ):SelectedTrackState
}