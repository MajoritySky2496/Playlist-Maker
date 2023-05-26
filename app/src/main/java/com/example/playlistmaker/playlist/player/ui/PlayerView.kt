package com.example.playlistmaker.playlist.player.ui

interface PlayerView {
    fun setTheButtonImagePlay()
    fun setTheButtonImagePause()
    fun setTheButtonEnabledFalse()
    fun setTheButtonEnabledTrue()
    fun setTimerReset()
    fun preparePlayer()
    fun finishActivity()
    fun setTime()
    fun setTimeRefresh():Long
}