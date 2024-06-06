package com.example.playlistmaker.playlist.player.ui.models

sealed interface TrackTextState {

    data class showTrackText(val text: String) : TrackTextState
}