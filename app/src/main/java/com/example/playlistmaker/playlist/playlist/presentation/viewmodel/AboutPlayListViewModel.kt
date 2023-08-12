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
    var numberOfMinutes = "Треков нет"


    private var getPlayListJob: Job? = null
    private var getTracksJob:Job? = null

    private var aboutPlayListStateLiveData = MutableLiveData<AboutPlayListState>()
    fun getAboutPlayListStateLiveData():LiveData<AboutPlayListState> = aboutPlayListStateLiveData

    fun getPlayList(id:Int?){
        getPlayListJob = viewModelScope.launch{
             interactor.getPlayList(id).collect{pair ->
                 playList = pair
                 getTracks(playList?.idTracks)
             }
        }

    }
    private fun getTracks(idTrack:String?){
        if(idTrack!=null) {
            getTracksJob = viewModelScope.launch {
                interactor.getTrackList(idTrack).collect { pair ->
                    tracks.clear()
                    tracks.addAll(pair)
                    numberOfMinutes = trackDuration(tracks)



                    aboutPlayListStateLiveData.postValue(
                        AboutPlayListState.ShowInfOfPlayList(
                            playList!!,
                            tracks,
                            numberOfMinutes
                        )
                    )


                }

            }
        }else{
            aboutPlayListStateLiveData.postValue(
                AboutPlayListState.ShowInfOfPlayList(
                    playList!!,
                    tracks,
                    numberOfMinutes
                )
            )
        }
        }
    private fun trackDuration(tracks:List<Track>):String{
        var trackDuration = 0L
        tracks.map { trackDuration = it.trackTimeMillis.plus(trackDuration) }
        val minute = (trackDuration/60000).toInt()
        when(minute%10){
            1 -> numberOfMinutes = minute.toString().plus(" минута")
            2,3,4 -> numberOfMinutes = minute.toString().plus(" минуты")
            else -> numberOfMinutes = minute.toString().plus(" минут")
        }



        return numberOfMinutes

    }
}