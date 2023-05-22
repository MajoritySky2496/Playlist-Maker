package com.example.playlistmaker.playlist.player.ui.models

sealed interface Taimer{
    data class SetTimeReset(var timeReset:String):Taimer
    data class TimeUpdate(var currentPosition:Int):Taimer
}