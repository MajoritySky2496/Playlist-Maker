package com.example.soundhaven.playlist.player.ui.models

import com.example.soundhaven.playlist.search.domain.models.Track

sealed interface TrackScreenState{
    object Loading:TrackScreenState
    object Content:TrackScreenState
    data class DrawTrack(val track: Track, var isFavotite:Boolean):TrackScreenState

}