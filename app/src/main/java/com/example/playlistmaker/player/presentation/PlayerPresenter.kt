package com.example.playlistmaker.player.presentation

import android.content.Intent
import android.media.MediaPlayer
import com.example.playlistmaker.Track
import com.example.playlistmaker.data.TracksRouter
import com.example.playlistmaker.player.AudioPlayerActivity

open class PlayerPresenter(private var view: PlayerView?) {
    lateinit var playerState:PlayerState
    var isClicked = false
    val router = TracksRouter()
    private var mediaPlayer = MediaPlayer()

    fun onPause(){
        pausePlayer()
    }

    fun onViewDestroyed() {
        view = null
        mediaPlayer.release()

    }
    fun onStop(){
        pausePlayer()
    }



    fun getTrack(intent: Intent): Track {
        return router.getTruck(intent)
    }

//    fun playButtonCliked() {
//        isClicked = !isClicked
//        if (isClicked) {
//            view?.setTheButtonImagePause()
//            startPlayer()
//            view?.setTimerPlay()
//        } else {
//            view?.setTheButtonImagePlay()
//            pausePlayer()
//            view?.setTimerPause()
//        }
//
//    }



    fun preparePlayer(url: String) {
        if(url==null){
            view?.setTheButtonEnabledFalse()
        }
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        playerState = PlayerState.STATE_PREPARED

        mediaPlayer.setOnPreparedListener {
            view?.setTheButtonEnabledTrue()
            playerState = PlayerState.STATE_PREPARED

        }
        mediaPlayer.setOnCompletionListener {
            view?.setTimerPause()
            view?.setTheButtonImagePlay()
            view?.setTimerStart()
            playerState = PlayerState.STATE_PREPARED

        }
    }
    private fun startPlayer(){
        mediaPlayer.start()
        view?.setTimerPause()
        playerState = PlayerState.STATE_PLAYING
        view?.setTimerStart()
    }
    private fun pausePlayer(){
        mediaPlayer.pause()
        view?.setTimerPause()
        playerState = PlayerState.STATE_PAUSED
        view?.setTimerPause()
    }
    fun playbackControl() {

        when (playerState) {
            PlayerState.STATE_PLAYING -> {
                pausePlayer()
            }
            PlayerState.STATE_PAUSED, PlayerState.STATE_PREPARED -> {
                startPlayer()
            }
            PlayerState.STATE_DEFAULT -> null
        }
    }
     fun backButton(){
        view?.finishActivity()
    }
    enum class PlayerState {
         STATE_DEFAULT,
         STATE_PREPARED,
         STATE_PLAYING,
        STATE_PAUSED,


    }




}
