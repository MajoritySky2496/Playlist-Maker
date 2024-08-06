package com.example.soundhaven.playlist.mediateca.presentation.model

import com.example.soundhaven.playlist.search.domain.models.Track

sealed interface SelectedTrackState {
    data class TrackContent(
        val tracks:List<Track>
    ): SelectedTrackState
    data class Error(
        val errorMessage:String
    ):SelectedTrackState
}