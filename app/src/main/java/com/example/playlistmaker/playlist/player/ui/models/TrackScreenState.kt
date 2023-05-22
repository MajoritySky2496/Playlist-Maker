package com.example.playlistmaker.playlist.player.ui.models

import com.example.playlistmaker.playlist.search.domain.models.Track

sealed interface TrackScreenState{
    object Loading:TrackScreenState
    data class Content(val track: Track):TrackScreenState

}