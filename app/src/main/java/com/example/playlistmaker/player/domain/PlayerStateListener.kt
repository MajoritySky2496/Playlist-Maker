package com.example.playlistmaker.player.domain

import com.example.playlistmaker.data.PlayerState
import com.example.playlistmaker.player.presentation.PlayerPresenter

interface PlayerStateListener {
    fun onStateChanged(state: PlayerState)



}