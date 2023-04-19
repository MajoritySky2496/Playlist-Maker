package com.example.playlistmaker.player.presentation

interface PlayerView {
    fun setTheButtonImagePlay()
    fun setTheButtonImagePause()
    fun setTheButtonEnabledFalse()
    fun setTheButtonEnabledTrue()
    fun setTimerPlay()
    fun setTimerPause()
    fun setTimerStart()
    fun preparePlayer()
    fun finishActivity()
//    fun startPlayer()
//    fun pausePlayer()


}