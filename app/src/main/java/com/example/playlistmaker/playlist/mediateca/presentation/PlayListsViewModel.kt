package com.example.playlistmaker.playlist.mediateca.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.playlist.mediateca.presentation.model.PlayListsScreenState
import com.example.playlistmaker.playlist.playlist.domain.PlayListInteractor
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PlayListsViewModel(private val interactor: PlayListInteractor):ViewModel() {


    private var getPlayListJob:Job? = null
    private var stateLiveData = MutableLiveData<PlayListsScreenState>()
    fun getStateLiveData(): LiveData<PlayListsScreenState> = stateLiveData
    init {
        getPlayLists()
    }

     fun getPlayLists(){
         getPlayListJob?.cancel()
        getPlayListJob = viewModelScope.launch {
            interactor.getPlayLists().collect{ pair ->
                when{
                    pair.isNotEmpty() -> {
                        var playList:MutableList<PlayList> = pair.toMutableList()
                        stateLiveData.postValue(PlayListsScreenState.showPlayLists(playList))


                    }

                }

            }
        }

    }
    fun cancel(){
        getPlayListJob?.cancel()
    }

//
}