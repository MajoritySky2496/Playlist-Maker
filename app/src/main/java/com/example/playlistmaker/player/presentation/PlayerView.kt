package com.example.playlistmaker.player.presentation

interface PlayerView {
    fun setTheButtonImagePlay()
    fun setTheButtonImagePause()
    fun setTheButtonEnabledFalse()
    fun setTheButtonEnabledTrue()
//    fun setTimerPlay()
//    fun setTimerPause()
    fun setTimerReset()
    fun preparePlayer()
    fun finishActivity()
    fun setTime()
    fun setTimeRefresh():Long
//    fun startPlayer()
//    fun pausePlayer()


}