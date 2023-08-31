package com.example.playlistmaker.playlist.playlist.presentation.viewmodel

import android.content.Context
import android.view.Gravity
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.player.ui.models.ToastScreenState
import com.example.playlistmaker.playlist.playlist.domain.PlayListInteractor
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import com.example.playlistmaker.playlist.playlist.ui.models.aboutplaylist.AboutPlayListState
import com.example.playlistmaker.playlist.playlist.ui.models.aboutplaylist.GoBackState
import com.example.playlistmaker.playlist.search.data.api.ResourceProvider
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.example.playlistmaker.playlist.sharing.domain.SharingInteractor
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_play_list.view.textView
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

class AboutPlayListViewModel(private val interactor: PlayListInteractor,
                             private val resourceProvider: ResourceProvider,
                             private val shareInteractor: SharingInteractor
):ViewModel() {

     var playList:PlayList? = null
    var tracks = mutableListOf<Track>()
    var numberOfMinutes = "Треков нет"
    private var getPlayListJob: Job? = null
    private var getTracksJob:Job? = null
    private var deleteTrack:Job? = null
    private var deletePlayListJob:Job? = null

    private var aboutPlayListStateLiveData = MutableLiveData<AboutPlayListState>()
    private val toastScreenState = MutableLiveData<ToastScreenState>()
    private val goBackStateLiveData = MutableLiveData<GoBackState>()
    fun getAboutPlayListStateLiveData():LiveData<AboutPlayListState> = aboutPlayListStateLiveData
    fun getToastScreenState():LiveData<ToastScreenState> = toastScreenState
    fun getGoBackStateLiveData():LiveData<GoBackState> = goBackStateLiveData

    fun getPlayList(id:Int?){
        getPlayListJob = viewModelScope.launch{
             interactor.getPlayList(id).collect{pair ->
                 playList = pair
                 getTracks(playList?.idTracks)
             }
        }

    }
    suspend fun getTracks(idTrack:String?){
        if(idTrack!=null) {
                interactor.getTrackList(idTrack).collect { pair ->
                    tracks.clear()
                    tracks.addAll(pair)
                    numberOfMinutes = trackDuration(tracks)
                    aboutPlayListStateLiveData.postValue(
                        AboutPlayListState.ShowInfOfPlayList(
                            playList,
                            tracks,
                            numberOfMinutes
                        )
                    )
                }


        }else{
            aboutPlayListStateLiveData.postValue(
                AboutPlayListState.ShowInfOfPlayList(
                    playList,
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

    fun openDialogDeleteTrack(context: Context, track: Track){


            MaterialAlertDialogBuilder(context, R.style.AlertDialogTheme)
                .setTitle(resourceProvider.getString(R.string.you_want_delete_track))
                .setMessage(resourceProvider.getString(R.string.delete_track))

                .setNegativeButton(resourceProvider.getString(R.string.yes)) { dialog, which ->
                    deleteTrack(track)
                }
                .setPositiveButton(resourceProvider.getString(R.string.no)) { dialog, which ->

                }.show()
    }
    fun createTextForDialog(context: Context, text:String):TextView{
        val textview = TextView(context)
        with(textview){
            textView.text = text
            textview.gravity= Gravity.RIGHT

        }
        return textview
    }
    fun openDialogDeletePlayList(context: Context, playList: PlayList){
        MaterialAlertDialogBuilder(context, R.style.AlertDialogTheme)
            .setMessage("${resourceProvider.getString(R.string.you_want_delete_playlist)} ${playList.name}?")
            .setNegativeButton(resourceProvider.getString(R.string.yes)) { dialog, which ->
                deletePlayList(playList.playListId!!)
            }
            .setPositiveButton(resourceProvider.getString(R.string.no)) { dialog, which ->

            }.show()
    }
    private fun deleteTrack(track:Track){
        deleteTrack = viewModelScope.launch {
            interactor.deleteTrackPlayList(track, playList!!, tracks)

        }
    }
    fun shareClick(playListCopy: PlayList, trackList:MutableList<Track>?){
        val message = StringBuilder()
        trackList?.forEachIndexed { index, track ->
           message.append("${index + 1}. " + "${track.artistName} - ${track.trackName} (${trackTime(track.trackTimeMillis)})\n")

        }
        val shareText = """
            |${playListCopy.name}
            |${playListCopy.description}
            |${playListCopy.numberTracks}
            |${message.toString()}""".trimMargin()
        if(trackList.isNullOrEmpty()){
            toastScreenState.postValue(ToastScreenState.toastText(resourceProvider.getString(R.string.no_tracks)))
        }else{
            shareInteractor.shareApp(shareText)

        }
    }
    fun trackTime(time: Int):String{
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(time)
    }
    fun deletePlayList(idPlayList:Int){

        deletePlayListJob = viewModelScope.launch {
            interactor.deletePlayList(idPlayList)
            goBackStateLiveData.postValue(GoBackState.GoBack)


        }

    }

}