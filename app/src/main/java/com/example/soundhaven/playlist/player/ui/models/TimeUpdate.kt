package com.example.soundhaven.playlist.player.ui.models

sealed interface Timer{
    data class SetTimeReset(var timeReset:String):Timer
    data class TimeUpdate(var currentPosition:Int):Timer
}