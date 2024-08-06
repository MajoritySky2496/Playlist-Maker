package com.example.soundhaven.playlist.player.ui.models

sealed interface TrackTextState {

    data class showTrackText(val text: String) : TrackTextState
}