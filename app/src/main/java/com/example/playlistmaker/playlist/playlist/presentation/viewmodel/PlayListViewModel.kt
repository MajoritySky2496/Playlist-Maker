package com.example.playlistmaker.playlist.playlist.presentation.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.playlist.domain.PlayListInteractor
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import com.example.playlistmaker.playlist.playlist.ui.models.createplaylist.CreatePlayListButtonStatus
import com.example.playlistmaker.playlist.playlist.ui.models.createplaylist.PlayListScreenState
import com.example.playlistmaker.playlist.search.data.api.ResourceProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class PlayListViewModel(private val interactor: PlayListInteractor, private val resourceProvider: ResourceProvider):ViewModel() {


    var playList = PlayList(null, "", "", null, null, null)
    lateinit var idImage:String

    private var  createPlayListButtonStatusLiveData = MutableLiveData<CreatePlayListButtonStatus>()
    private var playListScreenStateLiveData = MutableLiveData<PlayListScreenState>()
    var createPlayListButtonStatus = CreatePlayListButtonStatus(false)
    var insertPlayListJob: Job? = null


    init {
        createPlayListButtonStatusLiveData.value = createPlayListButtonStatus
    }

    fun getCreatePlayListButtonStatusLiveData(): LiveData<CreatePlayListButtonStatus> = createPlayListButtonStatusLiveData
    fun getPlayListStateLiveData(): LiveData<PlayListScreenState> = playListScreenStateLiveData

    open fun insertPlayList(){
        insertPlayListJob = viewModelScope.launch {
            idImage = playList.image.toString()
            saveImageToPrivateStorage(playList.image?.let { Uri.parse(it) })
            playList.image = getImage(idImage).toString()
            interactor.insertPlayList(playList)
            playListScreenStateLiveData.postValue(PlayListScreenState.Finish)


        }
    }

    fun showScreen(){
        playListScreenStateLiveData.postValue(PlayListScreenState.showScreen(playList))
    }

    fun unlockInsertBottom(){
        if(playList.name!!.isNotEmpty()){
            createPlayListButtonStatus.clickable = true
            createPlayListButtonStatusLiveData.value = createPlayListButtonStatus
        }else{
            createPlayListButtonStatus.clickable = false
            createPlayListButtonStatusLiveData.value = createPlayListButtonStatus
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
    suspend fun saveImageToPrivateStorage(uri: Uri?){
        if (uri!=null){
            interactor.saveImageToPrivateStorage(uri)
        }
    }
    suspend fun getImage(id:String): Uri{
         return interactor.getImage(id)
    }
    fun openDialog(context: Context){
        if(playList.name!!.isNotEmpty() || playList.image!=null || playList.description!!.isNotEmpty()){
            MaterialAlertDialogBuilder(context, R.style.AlertDialogTheme)
                .setTitle(resourceProvider.getString(R.string.finish_creating_a_playlist))
                .setMessage(resourceProvider.getString(R.string.all_unsaved_data_will_be_lost))
                .setNeutralButton(resourceProvider.getString(R.string.cancel)) { dialog, which ->

                }
                .setPositiveButton(resourceProvider.getString(R.string.complete)) { dialog, which ->

                        playListScreenStateLiveData.postValue(PlayListScreenState.Finish)
                }.show()
        } else{
            playListScreenStateLiveData.postValue(PlayListScreenState.Finish)
        }


    }
}