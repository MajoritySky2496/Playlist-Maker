package com.example.playlistmaker.playlist.player.domain.api

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