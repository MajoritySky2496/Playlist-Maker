package com.example.playlistmaker.playlist.player.presentation


import android.util.Log
import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.mediateca.domain.HistoryInteractor


import com.example.playlistmaker.playlist.player.domain.api.PlayerInteractor
import com.example.playlistmaker.playlist.player.ui.models.PlayStatus
import com.example.playlistmaker.playlist.player.ui.models.Timer
import com.example.playlistmaker.playlist.player.ui.models.TrackScreenState
import com.example.playlistmaker.playlist.search.data.api.ResourceProvider
import com.example.playlistmaker.playlist.search.domain.models.Track
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class PlayerViewModel(
    private val interactor: PlayerInteractor,
    private val track: Track,
    resourceProvider: ResourceProvider,
    private val historyInteractor: HistoryInteractor
) :

    ViewModel() {

    var url = track.previewUrl
    private var timerJob: Job? = null
    lateinit var playStatus: PlayStatus
    var isFavorite = false

    init {

        url?.let { interactor.preparePlayer(it) }
        interactor.setOnPreparedListener {
            screenStateLiveData.postValue(TrackScreenState.Content)
        }
        interactor.setOnCompletionListener {
            timerJob?.cancel()
            timer(Timer.SetTimeReset(resourceProvider.getString(R.string.startTime)))
            playStatusLiveData.value = getCurrentPlayStatus().copy(isPlaying = false)

        }


    }

    private val screenStateLiveData = MutableLiveData<TrackScreenState>(TrackScreenState.Loading)
    private val playStatusLiveData = MutableLiveData<PlayStatus>()

    private val timerSatusLiveData = MutableLiveData<Timer>()
    private var insertTrackJob: Job? = null

    private fun timer(timer: Timer) {
        timerSatusLiveData.postValue(timer)
    }

    fun drawTrack() {
        screenStateLiveData.postValue(TrackScreenState.DrawTrack(track, isFavorite))
    }

    fun getScreenStateLiveData(): LiveData<TrackScreenState> = screenStateLiveData
    fun getPlayStatusLiveData(): LiveData<PlayStatus> = playStatusLiveData

    fun getTaimerStatusLiveData(): LiveData<Timer> = timerSatusLiveData

    fun onFavoriteClicked() {
        insertTrackJob = viewModelScope.launch {

            when (track.isFavorite) {
                true -> {
                    track.isFavorite = false
                    historyInteractor.deleteTrack(track)
                    isFavorite = false
                    screenStateLiveData.postValue(TrackScreenState.DrawTrack(track, isFavorite))


                }

                false -> {
                    track.isFavorite = true
                    historyInteractor.insertTrack(track)
                    isFavorite = true
                    screenStateLiveData.postValue(TrackScreenState.DrawTrack(track, isFavorite))


                }
            }
        }
    }

    fun checkIsFavoriteCliked() {
        when (track.isFavorite) {
            true -> isFavorite = true
            false -> isFavorite = false
        }
    }

    fun play() {
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
        screenStateLiveData.postValue(TrackScreenState.DrawTrack(track, isFavorite))
        startTimer()
    }

    fun playBackControl() {
        playStatus = getCurrentPlayStatus()
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
    }
    private fun startTimer() {
        timerJob = viewModelScope.launch {

            while (playStatus.isPlaying.equals(false)) {

                delay(300L)
                Log.d("myLog", "Повторение ")
                timer(Timer.TimeUpdate(interactor.getCurrentPosition()))
            }

        }
    }

    fun pause() {
        interactor.pausePlayer()
        playStatusLiveData.value = getCurrentPlayStatus().copy(isPlaying = false)
        timerJob?.cancel()
    }

    companion object {
        private const val DELAY_MILLIS = 100L
        private const val TIMERESET = "00:00"

    }

}