package com.example.playlistmaker.playlist.playlist.ui.models.createplaylist

import com.example.playlistmaker.playlist.playlist.domain.models.PlayList

sealed interface PlayListScreenState{

    object Finish: PlayListScreenState
    data class showScreen(val playList: PlayList): PlayListScreenState
}
