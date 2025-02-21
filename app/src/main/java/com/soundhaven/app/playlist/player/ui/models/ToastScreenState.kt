package com.soundhaven.app.playlist.player.ui.models

import com.soundhaven.app.playlist.playlist.domain.models.PlayList

sealed interface ToastScreenState {

    data class showToast(val playList: PlayList): ToastScreenState
    data class toastText(val text: String): ToastScreenState
}