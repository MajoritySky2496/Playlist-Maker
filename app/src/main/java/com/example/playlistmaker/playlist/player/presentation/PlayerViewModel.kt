package com.example.playlistmaker.playlist.player.presentation


import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

import com.example.playlistmaker.playlist.creator.Creator
import com.example.playlistmaker.playlist.player.domain.api.IPlayerInteractor
import com.example.playlistmaker.playlist.player.ui.models.PlayStatus
import com.example.playlistmaker.playlist.player.ui.models.Taimer
import com.example.playlistmaker.playlist.player.ui.models.TrackScreenState
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.google.android.material.internal.ContextUtils.getActivity


class PlayerViewModel(private val interactor: IPlayerInteractor, private val track: Track) :
    ViewModel() {
     var url = track.previewUrl


    private var handler = Handler(Looper.getMainLooper())
    private val screenStateLiveData = MutableLiveData<TrackScreenState>(TrackScreenState.Loading)
    private val playStatusLiveData = MutableLiveData<PlayStatus>()
    private val taimerSatusLiveData = MutableLiveData<Taimer>()

    private fun taimer(taimer: Taimer){
        taimerSatusLiveData.postValue(taimer)
    }

    init {
        url?.let { interactor.preparePlayer(it) }
        interactor.setOnPreparedListener {
            screenStateLiveData.postValue(TrackScreenState.Content(track))
        }
        interactor.setOnCompletionListener {
            handler.removeCallbacks(timeUpdate)
            taimer(Taimer.SetTimeReset(TIMERESET))
            playStatusLiveData.value = getCurrentPlayStatus().copy(isPlaying = false)

        }

    }

    fun getScreenStateLiveData(): LiveData<TrackScreenState> = screenStateLiveData
    fun getPlayStatusLiveData(): LiveData<PlayStatus> = playStatusLiveData
    fun getTaimerStatusLiveData():LiveData<Taimer> = taimerSatusLiveData

    fun play() {
        handler.postDelayed(timeUpdate, DELAY
        )
        interactor.startPlayer(
            statusObserver = object : IPlayerInteractor.StatusObserver {

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
            taimer(Taimer.TimeUpdate(interactor.getCurrentPosition()))
            setTimeRefresh().let { handler.postDelayed(this, it) }
        }
    }

    fun pause() {
        interactor.pausePlayer()
        playStatusLiveData.value = getCurrentPlayStatus().copy(isPlaying = false)
        handler.removeCallbacks(timeUpdate)
    }


    fun setTimeRefresh(): Long {
        return DELAY
    }




    companion object {
        private const val DELAY = 100L
        private const val TIMERESET = "00:00"
        fun getViewModelFactory(track: Track): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                PlayerViewModel(
                    interactor = Creator.providePlayerInteractor(),
                    track
                )

            }
        }
    }

}