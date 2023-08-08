package com.example.playlistmaker.playlist.player.ui.models

import com.example.playlistmaker.playlist.playlist.domain.models.PlayList

sealed interface ToastScreenState {

    data class showToast(val playList: PlayList): ToastScreenState
}