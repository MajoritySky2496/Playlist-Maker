package com.example.playlistmaker.playlist.playlist.presentation

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.playlist.domain.PlayListInteractor
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import com.example.playlistmaker.playlist.playlist.ui.models.CreatePlayListButtonStatus
import com.example.playlistmaker.playlist.playlist.ui.models.PlayListScreenState
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PlayListViewModel(private val interactor: PlayListInteractor):ViewModel() {


     var playList = PlayList(0, "", "", null, null, null)
    var idImage = playList.name.toString()

    private var  createPlayListButtonStatusLiveData = MutableLiveData<CreatePlayListButtonStatus>()
    private var playListScreenStateLiveData = MutableLiveData<PlayListScreenState>()
    var createPlayListButtonStatus = CreatePlayListButtonStatus(false)
    var insertPlayListJob: Job? = null

    init {
        createPlayListButtonStatusLiveData.value = createPlayListButtonStatus
    }

    fun getCreatePlayListButtonStatusLiveData(): LiveData<CreatePlayListButtonStatus> = createPlayListButtonStatusLiveData
    fun getPlayListStateLiveData(): LiveData<PlayListScreenState> = playListScreenStateLiveData






    fun insert(){
        insertPlayListJob = viewModelScope.launch {
            saveImageToPrivateStorage(playList.image?.let { Uri.parse(it) })
            Log.d("mage", "${getImage()}")
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
            createPlayListButtonStatusLiveData.value = createPlayListButtonStatus}

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
        Log.d("myLog", "${playList.image}")
    }
    suspend fun saveImageToPrivateStorage(uri: Uri?){
        if (uri!=null){
            interactor.saveImageToPrivateStorage(uri, idImage)
        }
    }
    suspend fun getImage(): Uri?{
         return playList.image?.let { interactor.getImage(it) }
    }
    fun openDialog(context: Context){
        if(playList.name!!.isNotEmpty() || playList.image!=null || playList.description!!.isNotEmpty()){
            MaterialAlertDialogBuilder(context, R.style.AlertDialogTheme)
                .setTitle("Завершить создание плейлиста?") // Заголовок диалога
                .setMessage("Все несохраненные данные будут потеряны") // Описание диалога
                .setNeutralButton("Отмена") { dialog, which -> // Добавляет кнопку "Отмена"
                    // Действия, выполняемые при нажатии на кнопку "Отмена"
                }
                .setPositiveButton("Завершить") { dialog, which -> // Добавляет кнопку "Да"

                        playListScreenStateLiveData.postValue(PlayListScreenState.Finish)
                }.show()
        } else{
            playListScreenStateLiveData.postValue(PlayListScreenState.Finish)
        }


    }
     fun finish(context: Context){
         if(playList.name!=null || playList.image!=null || playList.description!=null){
             openDialog(context)
         } else
             playListScreenStateLiveData.postValue(PlayListScreenState.Finish)

    }
}