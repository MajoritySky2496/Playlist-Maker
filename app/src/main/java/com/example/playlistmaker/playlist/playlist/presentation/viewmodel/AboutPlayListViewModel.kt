package com.example.playlistmaker.playlist.playlist.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.playlist.playlist.domain.PlayListInteractor
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import com.example.playlistmaker.playlist.playlist.ui.models.aboutplaylist.AboutPlayListState
import com.example.playlistmaker.playlist.search.data.api.ResourceProvider
import com.example.playlistmaker.playlist.search.domain.models.Track
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AboutPlayListViewModel(private val interactor: PlayListInteractor, private val resourceProvider: ResourceProvider):ViewModel() {

     var playList:PlayList? = null
    var tracks = mutableListOf<Track>()

    private var getPlayListJob: Job? = null
    private var getTracksJob:Job? = null

    private var aboutPlayListStateLiveData = MutableLiveData<AboutPlayListState>()
    fun getAboutPlayListStateLiveData():LiveData<AboutPlayListState> = aboutPlayListStateLiveData

    private fun getPlayList(id:Int?):PlayList?{
        getPlayListJob = viewModelScope.launch{
             interactor.getPlayList(id).collect{pair ->
                 playList = pair

             }

        }
        return playList
    }
    private fun getTracks(idTrack:String?):MutableList<Track>{
        getTracksJob = viewModelScope.launch {
            interactor.getTrackList(idTrack).collect { pair ->

                if(pair.isNotEmpty()){
                    tracks.clear()
                    tracks.addAll(pair)
                }
                }

            }
        return tracks


        }

    fun showScreen(idPlayList:Int?){
        val trackId = getPlayList(idPlayList)?.idTracks
        aboutPlayListStateLiveData.postValue(AboutPlayListState.ShowInfOfPlayList(getPlayList(idPlayList)!!, getTracks(trackId)))


    }



}