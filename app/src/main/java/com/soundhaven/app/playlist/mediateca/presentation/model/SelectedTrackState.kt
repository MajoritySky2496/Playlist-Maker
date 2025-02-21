package com.soundhaven.app.playlist.mediateca.presentation.model

import com.soundhaven.app.playlist.search.domain.models.Track

sealed interface SelectedTrackState {
    data class TrackContent(
        val tracks:List<Track>
    ): SelectedTrackState
    data class Error(
        val errorMessage:String
    ):SelectedTrackState
}