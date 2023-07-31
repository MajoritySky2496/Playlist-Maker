package com.example.playlistmaker.playlist.playlist.presentation

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.playlist.playlist.domain.PlayListInteractor
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import com.example.playlistmaker.playlist.playlist.ui.models.CreatePlayListButtonStatus
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PlayListViewModel(private val interactor: PlayListInteractor):ViewModel() {


     var playList = PlayList(0, null, null, null, null, null)
    var idImage = playList.name.toString()

    private var  createPlayListButtonStatusLiveData = MutableLiveData<CreatePlayListButtonStatus>()
    var createPlayListButtonStatus = CreatePlayListButtonStatus(false)
    var insertPlayListJob: Job? = null

    init {
        createPlayListButtonStatusLiveData.value = createPlayListButtonStatus

    }

    fun getCreatePlayListButtonStatusLiveData(): LiveData<CreatePlayListButtonStatus> = createPlayListButtonStatusLiveData






    fun insert(){
        insertPlayListJob = viewModelScope.launch {
            saveImageToPrivateStorage(Uri.parse(playList.image))
            val uri = getImage()
            Log.d("mage", "$uri")
            interactor.insertPlayList(playList)


        }
    }

    fun unlockInsertBottom(){
        if(playList.name !=null){
            createPlayListButtonStatus.clickable = true
            createPlayListButtonStatusLiveData.value = createPlayListButtonStatus
        }else{
            TODO()

        }
    }
    fun addName(name:String){
        playList.name = name
        Log.d("name", playList.name.toString())

    }
    fun addDesription(description:String){
        playList.description = description
    }
    fun addImage(image:String){
        playList.image = image
    }
    suspend fun saveImageToPrivateStorage(uri: Uri?){
        interactor.saveImageToPrivateStorage(uri, idImage)
    }
    suspend fun getImage(): Uri{
         return interactor.getImage(playList.image!!)
    }
}