package com.example.playlistmaker.playlist.search.presentation


import android.app.Application
import android.os.Handler
import android.os.Looper
import android.os.SystemClock

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.creator.Creator
import com.example.playlistmaker.playlist.search.domain.api.TrackSearchInteractor
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.example.playlistmaker.playlist.search.ui.tracks.models.TrackSearchState


class TracksSearchViewModel(application: Application) : AndroidViewModel(application) {
    private val interactor = Creator.provideTracksInteractor(getApplication<Application>())

    companion object {
        const val SEARCH_DEBOUNCE_DELAY = 2000L
        val SEARCH_REQUEST_TOKEN = Any()
    }

    private var lastSearchText: String? = null
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
                                    errorMessage = getApplication<Application>().getString(R.string.no_connection)
                                )
                            )
                        }
                        tracks.isEmpty() -> {
                            renderState(
                                TrackSearchState.Empty(
                                    message = getApplication<Application>().getString(R.string.nothing_found)
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
}