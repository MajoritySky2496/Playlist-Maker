package com.example.playlistmaker.playlist.search.presentation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.search.data.api.ResourceProvider
import com.example.playlistmaker.playlist.search.domain.TrackSearchInteractor
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.example.playlistmaker.playlist.search.domain.models.models.TrackSearchState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch


class TracksSearchViewModel(
    private val interactor: TrackSearchInteractor,
    private val resourceProvider: ResourceProvider


) : ViewModel() {
    var trackHistory = mutableListOf<Track>()


    private var lastSearchText: String? = null
    private var debounceJob: Job? = null
    private var searchJob: Job? = null
    private var getHistoryTracksJob:Job? = null

    private val stateLiveData = MutableLiveData<TrackSearchState>()
    init {
        getHistoryTracks()
    }

    fun observeState(): LiveData<TrackSearchState> = stateLiveData
    private fun renderState(state: TrackSearchState) {
        stateLiveData.postValue(state)
    }

    fun getHistoryTracks(){
        getHistoryTracksJob = viewModelScope.launch {
            interactor.getTrack().collect{ value ->
                trackHistory.clear()
                trackHistory.addAll(value)
                renderState(TrackSearchState.HistroryContent(historyTrack = trackHistory))
            }


        }
    }
    fun onSearchTextChanged(changedText: String?) {

        if (changedText.isNullOrEmpty()) {
            debounceJob?.cancel()
            searchJob?.cancel()
            lastSearchText = null
            renderState(TrackSearchState.HistroryContent(historyTrack = trackHistory))
        } else {
            searchDebounce(changedText)
        }
    }

    fun searchDebounce(changedText: String) {


            lastSearchText = changedText
            debounceJob?.cancel()
            debounceJob = viewModelScope.launch {
                delay(SEARCH_DEBOUNCE_DELAY)
                searchTrack(changedText)
            }


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
        debounceJob?.cancel()
        searchJob?.cancel()
        lastSearchText = null
        renderState(TrackSearchState.HistroryContent(historyTrack = trackHistory))
    }

    private fun writeTrackHistory() {
        interactor.writeTrack(trackHistory)
    }

    fun removeTrackHistory() {
        trackHistory.clear()
        writeTrackHistory()
    }



     fun searchTrack(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(TrackSearchState.Loading)

            searchJob= viewModelScope.launch {
                interactor.searchTrack(newSearchText)
                    .collect { pair ->
                        val tracks = mutableListOf<Track>()
                        if (pair.first != null) {
                            tracks.addAll(pair.first!!)
                        }
                        when {
                            pair.second != null -> {
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
        }
    }
}

companion object {
    const val SEARCH_DEBOUNCE_DELAY = 2000L
}
}