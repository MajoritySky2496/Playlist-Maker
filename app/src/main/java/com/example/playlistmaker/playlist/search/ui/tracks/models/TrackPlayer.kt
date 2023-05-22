package com.example.playlistmaker.playlist.search.ui.tracks.models

import java.text.FieldPosition

interface TrackPlayer {
    fun play(statusObserver: StatusObserver)
    fun pause()
    fun seek(position: Float)

    fun release()

    interface StatusObserver{
        fun onProgress(progress:Float)
        fun onStop()
        fun onPlay()
    }
}