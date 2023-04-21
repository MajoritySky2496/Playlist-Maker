package com.example.playlistmaker.player.domain

import android.media.MediaPlayer
import com.example.playlistmaker.data.PlayerState

class Player {
    private lateinit var mediaplayer:MediaPlayer
    var listener: PlayerStateListener? = null

    init {
        listener?.onStateChanged(PlayerState.NOT_READY)
            mediaplayer.setOnPreparedListener {
                listener?.onStateChanged(PlayerState.STATE_PREPARED)
            }
            mediaplayer.setOnCompletionListener{
                listener?.onStateChanged(PlayerState.NOT_READY)

            }
        }
    }
