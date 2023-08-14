package com.example.playlistmaker.playlist.player.presentation


import android.util.Log
import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.mediateca.domain.HistoryInteractor


import com.example.playlistmaker.playlist.player.domain.api.PlayerInteractor
import com.example.playlistmaker.playlist.player.ui.models.BottomSheetScreenState
import com.example.playlistmaker.playlist.player.ui.models.PlayStatus
import com.example.playlistmaker.playlist.player.ui.models.Timer
import com.example.playlistmaker.playlist.player.ui.models.ToastScreenState
import com.example.playlistmaker.playlist.player.ui.models.TrackScreenState
import com.example.playlistmaker.playlist.playlist.domain.PlayListInteractor
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import com.example.playlistmaker.playlist.search.data.api.ResourceProvider
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.google.gson.Gson
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class PlayerViewModel(
    private val interactor: PlayerInteractor,
    private val interactorPlayList:PlayListInteractor,
    private val track: Track,

    resourceProvider: ResourceProvider,
    private val historyInteractor: HistoryInteractor
) :

    ViewModel() {

    var url = track.previewUrl
    private var timerJob: Job? = null
    private var getPlayListJobPlayer:Job? = null
    lateinit var playStatus: PlayStatus
    var isFavorite = false
    var playList = mutableListOf<PlayList>()
    var insertTrack: Job? = null
    val trackId:MutableList<String> = mutableListOf()

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
    private val bottomSheetScreenStateLiveData = MutableLiveData<BottomSheetScreenState>()
    private val toastScreenState = MutableLiveData<ToastScreenState>()

    private val timerSatusLiveData = MutableLiveData<Timer>()
    private var insertTrackJob: Job? = null

    private fun timer(timer: Timer) {
        timerSatusLiveData.postValue(timer)

    }

    fun drawTrack() {
        screenStateLiveData.postValue(TrackScreenState.DrawTrack(track, isFavorite))
    }
    fun getPlayLists(){

        getPlayListJobPlayer = viewModelScope.launch {
            interactorPlayList.getPlayLists().collect{ pair ->
                when{
                    pair.isNotEmpty() -> {
                        playList.clear()
                        playList.addAll(pair)
                        bottomSheetScreenStateLiveData.postValue(BottomSheetScreenState.ShowPlayLists(playList))
                    }

                }

            }
        }
    }
    fun insertTrack(playList: PlayList){
        insertTrack = viewModelScope.launch {
            playList.idTracks?.let { fromStringToList(it) }?.let { trackId.addAll(it) }
            if(trackId.contains(track.trackId)){
                trackId.clear()
                toastScreenState.postValue(ToastScreenState.showToast(playList))
            }else{
                bottomSheetScreenStateLiveData.postValue(BottomSheetScreenState.CloseBottomSheet(playList))
                interactorPlayList.insertTrackPlayList(track, playList)
            }
        }
    }
    fun insertTrackCancel(){
        insertTrack?.cancel()
    }

    fun getScreenStateLiveData(): LiveData<TrackScreenState> = screenStateLiveData
    fun getPlayStatusLiveData(): LiveData<PlayStatus> = playStatusLiveData
    fun getBottomSheetScreenStateLiveData():LiveData<BottomSheetScreenState> = bottomSheetScreenStateLiveData

    fun getTaimerStatusLiveData(): LiveData<Timer> = timerSatusLiveData
    fun getToastScreenState():LiveData<ToastScreenState> = toastScreenState

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
    fun fromStringToList(value: String): List<String> {
        val json = Gson().fromJson(value, Array<String>::class.java)
        return json.toList()
    }

}