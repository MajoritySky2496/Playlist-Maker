package com.soundhaven.app.playlist.player.ui.models

sealed interface TrackTextState {

    data class showTrackText(val text: String) : TrackTextState
}