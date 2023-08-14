package com.example.playlistmaker.playlist.playlist.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.playlist.playlist.domain.PlayListInteractor
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import com.example.playlistmaker.playlist.search.data.api.ResourceProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PlayListRedactorViewModel(private val interactor: PlayListInteractor, private val resourceProvider: ResourceProvider):PlayListViewModel(interactor, resourceProvider) {

    override var playList = PlayList(null, "", "", null, null, null)
    var updateJob: Job? = null

    override fun insertPlayList() {
        super.insertPlayList()
    }

    override fun addDesription(description: String) {
        super.addDesription(description)
        playList.description = description
    }

    override fun addName(name: String) {
        super.addName(name)
        playList.name = name
        Log.d("newLog", "$playList")
    }
    fun update(playListOld: PlayList){
        updateJob = viewModelScope.launch {
            val playListNew = playListOld.copy(
                name = playList.name,
                description = playList.description,
                image = checkImage(playList, playListOld)
            )
            interactor.updatePlayList(playListNew)
        }


    }
    suspend fun checkImage(playList: PlayList, playListOld: PlayList):String?{
        if(playList.image.isNullOrEmpty()){
            return playListOld.image
        }else{
            return playList.image
        }
    }





}