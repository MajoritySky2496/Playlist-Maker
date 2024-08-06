package com.example.soundhaven.playlist.player.ui.models

import com.example.soundhaven.playlist.playlist.domain.models.PlayList

sealed interface ToastScreenState {

    data class showToast(val playList: PlayList): ToastScreenState
    data class toastText(val text: String): ToastScreenState
}