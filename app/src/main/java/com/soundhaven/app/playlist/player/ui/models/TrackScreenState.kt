package com.soundhaven.app.playlist.player.ui.models

import com.soundhaven.app.playlist.search.domain.models.Track

sealed interface TrackScreenState{
    object Loading:TrackScreenState
    object Content:TrackScreenState
    data class DrawTrack(val track: Track, var isFavotite:Boolean):TrackScreenState

}