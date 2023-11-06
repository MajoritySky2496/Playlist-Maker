package com.example.playlistmaker.playlist.mediateca.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.mediateca.domain.HistoryInteractor
import com.example.playlistmaker.playlist.mediateca.presentation.model.SelectedTrackState
import com.example.playlistmaker.playlist.search.data.api.ResourceProvider
import com.example.playlistmaker.playlist.search.domain.models.Track
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SelectedTracksViewModel(
    private val interactor: HistoryInteractor,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val selectedTrack = mutableListOf<Track>()
    private var getSelectedJob: Job? = null
    private val stateLiveData = MutableLiveData<SelectedTrackState>()
    fun observeState(): LiveData<SelectedTrackState> = stateLiveData

    fun getSelectedTrack() {
        getSelectedJob = viewModelScope.launch {
            interactor.historyTrack()
                .collect { pair ->
                    if (pair.first != null) {
                        selectedTrack.clear()
                        selectedTrack.addAll(sortSelectTrack(pair.first!!))

                    }
                    when {
                        pair.second != null -> {
                            stateLiveData.postValue(
                                SelectedTrackState.Error(
                                    resourceProvider.getString(
                                        R.string.Mediateca_is_empty
                                    )
                                )
                            )

                        }

                        else -> {
                            stateLiveData.postValue(SelectedTrackState.TrackContent(selectedTrack))
                        }

                    }
                }
        }

    }

    fun sortSelectTrack(tracks: List<Track>): List<Track> {
        return tracks.reversed()
    }
}