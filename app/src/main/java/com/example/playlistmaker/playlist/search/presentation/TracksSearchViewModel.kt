package com.example.playlistmaker.playlist.search.presentation


import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.SystemClock

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.R

import com.example.playlistmaker.playlist.main.app.App
import com.example.playlistmaker.playlist.player.presentation.PlayerViewModel
import com.example.playlistmaker.playlist.search.data.ResourceProviderImpl
import com.example.playlistmaker.playlist.search.domain.api.ResourceProvider
import com.example.playlistmaker.playlist.search.domain.api.TrackSearchInteractor
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.example.playlistmaker.playlist.search.ui.tracks.models.TrackSearchState


class TracksSearchViewModel(
    private val interactor: TrackSearchInteractor,
    private val resourceProvider: ResourceProvider

    ) : ViewModel() {
    var trackHistory = mutableListOf<Track>()



    private var handler = Handler(Looper.getMainLooper())

    private val stateLiveData = MutableLiveData<TrackSearchState>()


    fun observeState(): LiveData<TrackSearchState> = stateLiveData
    private fun renderState(state: TrackSearchState) {
        stateLiveData.postValue(state)
    }

    init {
        trackHistory.addAll(interactor.getTrack())
        renderState(TrackSearchState.HistroryContent(historyTrack = trackHistory))
    }

    override fun onCleared() {
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
    }

    fun onSearchTextChanged(changedText:String) {

        if (changedText.isNullOrEmpty()) {
            renderState(TrackSearchState.HistroryContent(historyTrack = trackHistory))
        } else {
            searchDebounce(changedText)
        }
    }

    fun searchDebounce(changedText: String) {

        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
        val searchRunnable = Runnable {
            searchTrack(changedText)
        }
        val postTime = SystemClock.uptimeMillis() + SEARCH_DEBOUNCE_DELAY
        handler.postAtTime(
            searchRunnable,
            SEARCH_REQUEST_TOKEN,
            postTime
        )
    }

    fun refreshSearchTrack(newSearchText: String) {
        searchTrack(newSearchText)
    }

    fun trackAddInHistoryList(track: Track) {
        when {
            trackHistory.contains(track) -> {
                trackHistory.remove(track)
                trackHistory.add(0, track)
            }
            trackHistory.size < 10 -> {
                trackHistory.add(0, track)
            }
            else -> {
                trackHistory.removeAt(9)
                trackHistory.add(0, track)
            }

        }
        interactor.writeTrack(trackHistory)
    }

    fun loadTrackList(editText: String?) {
        if (editText.isNullOrEmpty()) {
            renderState(TrackSearchState.HistroryContent(historyTrack = trackHistory))
        }
    }

    fun clearInputEditText() {
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
        renderState(TrackSearchState.HistroryContent(historyTrack = trackHistory))


    }

    private fun writeTrackHistory() {
        interactor.writeTrack(trackHistory)
    }

    fun removeTrackHistory() {
        trackHistory.clear()
        writeTrackHistory()
    }

    fun setOnFocus(editText: String?, hasFocus: Boolean) {
        if (hasFocus && editText.isNullOrEmpty() && trackHistory.isNotEmpty()) {
            renderState(TrackSearchState.HistroryContent(historyTrack = trackHistory))
        }
    }

    private fun searchTrack(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(TrackSearchState.Loading)
            val tracks = mutableListOf<Track>()

            interactor.searchTrack(newSearchText, object : TrackSearchInteractor.TrackConsumer {
                override fun consume(foundTracks: List<Track>?, errorMessage: String?) {
                    if (foundTracks != null) {
                        tracks.addAll(foundTracks)

                    }
                    when {
                        errorMessage != null -> {
                            renderState(
                                TrackSearchState.Error(
                                    errorMessage = resourceProvider.getString(R.string.no_connection)
                                )
                            )
                        }
                        tracks.isEmpty() -> {
                            renderState(
                                TrackSearchState.Empty(
                                    message = resourceProvider.getString(R.string.nothing_found)
                                )
                            )
                        }
                        else -> {
                            renderState(TrackSearchState.TrackContent(tracks = tracks))
                        }
                    }
                }
            })
        }

    }
    companion object {
        const val SEARCH_DEBOUNCE_DELAY = 2000L
        val SEARCH_REQUEST_TOKEN = Any()


    }
}