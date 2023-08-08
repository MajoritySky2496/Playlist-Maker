package com.example.playlistmaker.playlist.player.domain.api

import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import kotlinx.coroutines.flow.Flow

interface PlayerInteractor {
    fun startPlayer(statusObserver: StatusObserver)
    fun pausePlayer()
    fun stopPlayer()
    fun getCurrentPosition():Int
    fun preparePlayer(url:String)
    fun release()
    fun setOnPreparedListener(listener: (Any) -> Unit)
    fun setOnCompletionListener(listener: (Any) -> Unit)


    interface StatusObserver {
        fun onStop()
        fun onPlay()
    }



}