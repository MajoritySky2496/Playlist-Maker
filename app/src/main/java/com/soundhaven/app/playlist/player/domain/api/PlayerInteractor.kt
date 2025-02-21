package com.soundhaven.app.playlist.player.domain.api

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

    fun getTrackText(artist: String, title: String): Flow<String>

    interface StatusObserver {
        fun onStop()
        fun onPlay()
    }

}