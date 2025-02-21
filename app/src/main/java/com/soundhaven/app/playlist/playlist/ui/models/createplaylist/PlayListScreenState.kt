package com.soundhaven.app.playlist.playlist.ui.models.createplaylist

import com.soundhaven.app.playlist.playlist.domain.models.PlayList

sealed interface PlayListScreenState{

    object Finish: PlayListScreenState
    data class showScreen(val playList: PlayList): PlayListScreenState
}
