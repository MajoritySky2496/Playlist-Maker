package com.example.playlistmaker.player.presentation

import com.example.playlistmaker.Track

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