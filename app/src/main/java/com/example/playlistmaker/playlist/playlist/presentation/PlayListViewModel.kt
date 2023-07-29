package com.example.playlistmaker.playlist.playlist.presentation

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.playlist.playlist.domain.PlayListInteractor
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList

class PlayListViewModel(private val interactor: PlayListInteractor):ViewModel() {


    lateinit var playList:PlayList





    suspend fun insert(playList:PlayList){
        interactor.insertPlayList(playList)

    }

    fun unlockInsertBottom(){
        if(playList.name!=null){
            TODO()
        }else{
            TODO()
        }
    }
    fun addName(name:String){
        playList.name = name

    }
    fun addDesription(description:String){
        playList.description = description
    }
    fun addImage(image:String){
        playList.image = image
    }
    fun saveImageToPrivateStorage(uri: Uri, id:String){
        interactor.saveImageToPrivateStorage(uri, id)
    }
    fun getImage(id:String): Uri{
         TODO()
    }
}