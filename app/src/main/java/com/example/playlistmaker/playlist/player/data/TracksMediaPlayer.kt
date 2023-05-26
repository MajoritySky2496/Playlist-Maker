package com.example.playlistmaker.playlist.player.data

import android.content.Context
import android.media.MediaPlayer
import com.example.playlistmaker.playlist.player.domain.api.MediaPlayerRepository

class TracksMediaPlayer:MediaPlayerRepository{
    private var mediaPlayer = MediaPlayer()

    override fun startPlayer() {
        mediaPlayer.start()
    }

    override fun pausePlayer() {
        mediaPlayer.pause()
    }

    override fun stopPlayer() {
        mediaPlayer.stop()
    }
    override fun preparePlayer(url:String){
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
    }
    override fun getCurrentPosition():Int {
        return mediaPlayer.currentPosition
    }

    override fun release() {
        mediaPlayer.release()
    }

    override fun setOnPreparedListener(listener: (Any) -> Unit) {
        mediaPlayer.setOnPreparedListener(listener)
    }

    override fun setOnCompletionListener(listener: (Any) -> Unit) {
        mediaPlayer.setOnCompletionListener(listener)
    }
}