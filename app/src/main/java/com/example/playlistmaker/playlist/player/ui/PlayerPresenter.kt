package com.example.playlistmaker.playlist.player.ui

import android.content.Intent

import android.os.Handler
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.example.playlistmaker.playlist.player.data.PlayerState
import com.example.playlistmaker.playlist.player.data.TracksRouter
import com.example.playlistmaker.playlist.player.domain.impl.PlayerInteractor

open class PlayerPresenter(private var view: PlayerView?, private val handler: Handler, private val intent: Intent) {
    lateinit var playerState:PlayerState

    val router = TracksRouter()
    var track = getTrack(intent)
    private var interactor = track.previewUrl?.let { PlayerInteractor(it) }
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
        interactor?.release()
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
            interactor?.setDataSource()
            interactor?.prepareAsync()

           playerState = PlayerState.STATE_PREPARED

            interactor?.setOnPreparedListener {
                view?.setTheButtonEnabledTrue()
               playerState = PlayerState.STATE_PREPARED

            }
            interactor?.setOnCompletionListener {
                handler.removeCallbacks(timeUpdate)
                view?.setTheButtonImagePlay()
                view?.setTimerReset()
                playerState = PlayerState.STATE_PREPARED

            }
        }
    }
    private fun startPlayer(){
        interactor?.startPlayer()
        view?.setTheButtonImagePause()
        view?.setTimeRefresh()?.let { handler.postDelayed(timeUpdate, it) }
        playerState = PlayerState.STATE_PLAYING
    }

    private fun pausePlayer(){
        interactor?.pausePlayer()
        view?.setTheButtonImagePlay()
        handler.removeCallbacks(timeUpdate)
       playerState = PlayerState.STATE_PAUSED
    }

    fun playbackControl() {
        when (playerState){
            PlayerState.STATE_PLAYING -> {
                pausePlayer()
            }
            PlayerState.STATE_PAUSED, PlayerState.STATE_PREPARED -> {
                view?.setTheButtonEnabledTrue()
                startPlayer()
            }
        }
        }

    fun getCurrentPosition():Int?{
        return interactor?.getcurrentPosition()
    }
     fun backButton(){
        view?.finishActivity()
    }
}
