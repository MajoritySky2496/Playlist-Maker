package com.example.playlistmaker.playlist.player.presentation


import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.R


import com.example.playlistmaker.playlist.player.domain.api.PlayerInteractor
import com.example.playlistmaker.playlist.player.ui.models.PlayStatus
import com.example.playlistmaker.playlist.player.ui.models.Timer
import com.example.playlistmaker.playlist.player.ui.models.TrackScreenState
import com.example.playlistmaker.playlist.search.domain.api.ResourceProvider
import com.example.playlistmaker.playlist.search.domain.models.Track


class PlayerViewModel(private val interactor: PlayerInteractor,
                      private val track: Track,
                      resourceProvider: ResourceProvider
) :

    ViewModel() {

    var url = track.previewUrl

    init {
        url?.let { interactor.preparePlayer(it) }
        interactor.setOnPreparedListener {
            screenStateLiveData.postValue(TrackScreenState.Content(track))
        }
        interactor.setOnCompletionListener {
            handler.removeCallbacks(timeUpdate)
            timer(Timer.SetTimeReset(resourceProvider.getString(R.string.startTime)))
            playStatusLiveData.value = getCurrentPlayStatus().copy(isPlaying = false)

        }

    }
    private var handler = Handler(Looper.getMainLooper())
    private val screenStateLiveData = MutableLiveData<TrackScreenState>(TrackScreenState.Loading)
    private val playStatusLiveData = MutableLiveData<PlayStatus>()
    private val timerSatusLiveData = MutableLiveData<Timer>()

    private fun timer(timer: Timer) {
        timerSatusLiveData.postValue(timer)
    }

    fun getScreenStateLiveData(): LiveData<TrackScreenState> = screenStateLiveData
    fun getPlayStatusLiveData(): LiveData<PlayStatus> = playStatusLiveData
    fun getTaimerStatusLiveData(): LiveData<Timer> = timerSatusLiveData

    fun play() {
        handler.postDelayed(
            timeUpdate, DELAY_MILLIS
        )
        interactor.startPlayer(
            statusObserver = object : PlayerInteractor.StatusObserver {

                override fun onStop() {
                    playStatusLiveData.value = getCurrentPlayStatus().copy(isPlaying = false)
                }

                override fun onPlay() {
                    playStatusLiveData.value = getCurrentPlayStatus().copy(isPlaying = true)

                }
            }
        )
        screenStateLiveData.postValue(TrackScreenState.Content(track))

    }

    fun playBackControl() {
        val playStatus = getCurrentPlayStatus()
        when (playStatus.isPlaying) {
            false -> play()
            true -> pause()
        }
    }

    private fun getCurrentPlayStatus(): PlayStatus {
        return playStatusLiveData.value ?: PlayStatus(false)
    }

    override fun onCleared() {
        interactor.release()
        handler.removeCallbacks(timeUpdate)


    }

    private val timeUpdate = object : Runnable {
        override fun run() {
            timer(Timer.TimeUpdate(interactor.getCurrentPosition()))
             handler.postDelayed(this, DELAY_MILLIS)
        }
    }

    fun pause() {
        interactor.pausePlayer()
        playStatusLiveData.value = getCurrentPlayStatus().copy(isPlaying = false)
        handler.removeCallbacks(timeUpdate)
    }

    companion object {
        private const val DELAY_MILLIS = 100L
        private const val TIMERESET = "00:00"

    }

}