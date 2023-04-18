package com.example.playlistmaker.player.presentation

import android.content.Intent
import android.media.MediaPlayer
import com.example.playlistmaker.Track
import com.example.playlistmaker.data.TracksRouter

open class PlayerPresenter(private var view: PlayerView?) {
    var isClicked = false
    val router = TracksRouter()
    private var mediaPlayer = MediaPlayer()


    fun getTrack(intent: Intent): Track {
        return router.getTruck(intent)
    }

    fun playButtonCliked() {
        isClicked = !isClicked
        if (isClicked) {
            view?.setTheButtonImagePause()
            startPlayer()
            view?.setTimerPlay()
        } else {
            view?.setTheButtonImagePlay()
            pausePlayer()
            view?.setTimerPause()
        }

    }

    fun onViewDestroyed() {
        view = null
    }

    private fun preparePlayer(url: String) {
        if(url==null){
            view?.setTheButtonEnabledFalse()
        }
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        PlayerState.STATE_PREPARED
    }
    private fun startPlayer(){
        mediaPlayer.start()
        PlayerState.STATE_PLAYING
    }
    private fun pausePlayer(){
        mediaPlayer.pause()
        PlayerState.STATE_PAUSED
    }
    enum class PlayerState {
         STATE_DEFAULT,
         STATE_PREPARED,
         STATE_PLAYING,
        STATE_PAUSED,


    }



}
