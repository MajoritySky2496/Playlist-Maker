package com.example.playlistmaker.playlist.player.domain.impl

import com.example.playlistmaker.playlist.player.data.TracksMediaPlayer

class PlayerInteractor(private var url:String?) {
    private  var tracksMediaPlayer = TracksMediaPlayer(url)

    fun startPlayer() {
        tracksMediaPlayer.startPlayer()
    }
    fun pausePlayer() {
        tracksMediaPlayer.pausePlayer()
    }
    fun stopPlayer(){
        tracksMediaPlayer.stopPlayer()
    }
    fun release(){
        tracksMediaPlayer.release()
    }

    fun setDataSource() {
        tracksMediaPlayer.setDataSource()
    }

    fun prepareAsync() {
        tracksMediaPlayer.prepareAsync()

    }
    fun getcurrentPosition():Int{
        return tracksMediaPlayer.getCurrentPosition()
    }
    fun setOnPreparedListener(listener: (Any) -> Unit){
        tracksMediaPlayer.setOnPreparedListener(listener)

    }
    fun setOnCompletionListener(listener: (Any) -> Unit){
        tracksMediaPlayer.onCompletionListener(listener)
    }

}