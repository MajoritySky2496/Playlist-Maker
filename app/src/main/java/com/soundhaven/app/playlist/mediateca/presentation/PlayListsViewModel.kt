package com.soundhaven.app.playlist.mediateca.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soundhaven.app.playlist.mediateca.presentation.model.PlayListsScreenState
import com.soundhaven.app.playlist.playlist.domain.PlayListInteractor
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PlayListsViewModel(private val interactor: PlayListInteractor):ViewModel() {

    private var getPlayListJob:Job? = null
    private var _stateLiveData = MutableLiveData<PlayListsScreenState>()
    fun getStateLiveData(): LiveData<PlayListsScreenState> = _stateLiveData
    init {
        getPlayLists()
    }

     fun getPlayLists(){
        getPlayListJob = viewModelScope.launch {
            interactor.getPlayLists().collect { pair ->
                _stateLiveData.postValue(PlayListsScreenState.showPlayLists(pair))

                }

            }
        }

    }



