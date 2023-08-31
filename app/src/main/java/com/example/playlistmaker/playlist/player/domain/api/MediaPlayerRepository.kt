package com.example.playlistmaker.playlist.player.domain.api

interface MediaPlayerRepository {
    fun startPlayer()
    fun pausePlayer()
    fun stopPlayer()
    fun getCurrentPosition():Int
    fun preparePlayer(url:String)
    fun release()
    fun setOnPreparedListener(listener: (Any) -> Unit)
    fun setOnCompletionListener(listener: (Any) -> Unit)

}