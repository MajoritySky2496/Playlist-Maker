package com.example.playlistmaker.playlist.player.data

import android.media.MediaPlayer

class TracksMediaPlayer(private var url: String?) {
    private var mediaPlayer = MediaPlayer()

    fun startPlayer() {
        mediaPlayer.start()
    }

    fun pausePlayer() {
        mediaPlayer.pause()
    }

    fun stopPlayer() {
        mediaPlayer.stop()
    }

    fun release() {
        mediaPlayer.release()
    }

    fun setDataSource() {
        mediaPlayer.setDataSource(url)
    }

    fun prepareAsync() {
        mediaPlayer.prepareAsync()
    }

    fun getCurrentPosition():Int {
        return mediaPlayer.currentPosition
    }

    fun setOnPreparedListener(listener: (Any) -> Unit) {
        mediaPlayer.setOnPreparedListener(listener)
    }

    fun onCompletionListener(listener: (Any) -> Unit) {
        mediaPlayer.setOnCompletionListener(listener)
    }

}