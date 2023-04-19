package com.example.playlistmaker.player.presentation

import android.content.Intent
import android.media.MediaPlayer
import android.os.Handler
import com.example.playlistmaker.Track
import com.example.playlistmaker.data.TracksRouter
import com.example.playlistmaker.player.AudioPlayerActivity
import java.text.SimpleDateFormat
import java.util.*

open class PlayerPresenter(private var view: PlayerView?, private val handler: Handler, private val intent: Intent) {
    lateinit var playerState:PlayerState


    val router = TracksRouter()
    private var mediaPlayer = MediaPlayer()
    var track = getTrack(intent)
    private val timeUpdate = object : Runnable {
        override fun run() {
            view?.setTime()
            view?.setTimeRefresh()?.let { handler.postDelayed(this, it) }
        }
    }

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

    fun preparePlayer() {

         var url = track.previewUrl
        if(url==null){
            view?.setTheButtonEnabledFalse()
        }else {
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepareAsync()

            playerState = PlayerState.STATE_PREPARED

            mediaPlayer.setOnPreparedListener {
                view?.setTheButtonEnabledTrue()
                playerState = PlayerState.STATE_PREPARED

            }
            mediaPlayer.setOnCompletionListener {
                handler.removeCallbacks(timeUpdate)
                view?.setTheButtonImagePlay()
                view?.setTimerReset()
                playerState = PlayerState.STATE_PREPARED

            }
        }
    }
    private fun startPlayer(){
        mediaPlayer.start()
        view?.setTheButtonImagePause()
        view?.setTimeRefresh()?.let { handler.postDelayed(timeUpdate, it) }
        playerState = PlayerState.STATE_PLAYING

    }
    private fun pausePlayer(){
        mediaPlayer.pause()
        view?.setTheButtonImagePlay()
        handler.removeCallbacks(timeUpdate)
        playerState = PlayerState.STATE_PAUSED

    }
    fun playbackControl() {

        when (playerState) {
            PlayerState.STATE_PLAYING -> {
                pausePlayer()
            }
            PlayerState.STATE_PAUSED, PlayerState.STATE_PREPARED -> {
                startPlayer()
            }

        }
    }
    fun getCurrentPosition():Int{
        return mediaPlayer.currentPosition
    }
     fun backButton(){
        view?.finishActivity()
    }

    enum class PlayerState {
         STATE_PREPARED,
         STATE_PLAYING,
        STATE_PAUSED,


    }




}
