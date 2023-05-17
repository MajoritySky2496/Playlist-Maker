package com.example.playlistmaker.playlist.search.presentation


import android.app.Application
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.View

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.creator.Creator
import com.example.playlistmaker.playlist.search.domain.api.TrackSearchInteractor
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.example.playlistmaker.playlist.search.ui.tracks.models.TrackState
import com.google.android.material.internal.ViewUtils


class TracksSearchViewModel(application: Application): AndroidViewModel(application){
    private val interactor = Creator.provideTracksInteractor(getApplication<Application>())
    companion object {
        const val PRODUCT_AMOUNT = "PRODUCT_AMOUNT"
        const val SEARCH_DEBOUNCE_DELAY = 2000L
        val SEARCH_REQUEST_TOKEN = Any()
    }
     var trackHistory = mutableListOf<Track>()
    val tracks = mutableListOf<Track>()
    private var handler = Handler(Looper.getMainLooper())
    private val stateLiveData = MutableLiveData<TrackState>()
    fun observeState():LiveData<TrackState> = stateLiveData

    private fun renderState(state: TrackState){
        stateLiveData.postValue(state)
    }
    init {
        trackHistory.addAll(interactor.getTrack())
        renderState(TrackState.HistroryContent(historyTrack =trackHistory))
    }

    override fun onCleared() {
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
    }
    fun onSearchTextChanged(query: String?) {

        if (query.isNullOrEmpty()) {
            renderState(TrackState.HistroryContent(historyTrack =trackHistory))
        } else {
            searchDebounce(query)
        }
    }

    fun searchDebounce(changedText:String){
//        if (lastSearchText == changedText){
//            return
//        }
//        this.lastSearchText = changedText
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
        val searchRunnable = Runnable { searchTrack(changedText)
        }
        val postTime = SystemClock.uptimeMillis() + SEARCH_DEBOUNCE_DELAY
        handler.postAtTime(
            searchRunnable,
            SEARCH_REQUEST_TOKEN,
            postTime
        )

    }
    fun refreshSearchTrack(newSearchText:String){
        searchTrack(newSearchText)
    }
    fun trackAddInHistoryList(track:Track){
        val historySet = LinkedHashSet<Track>()
        trackHistory.add(0, track)
        if (trackHistory.size > 11) {
            trackHistory.removeAt(trackHistory.size - 1)
        }
        historySet.addAll(trackHistory)
        trackHistory.clear()
        trackHistory.addAll(historySet)
        writeTrackHistory()
    }
     fun loadTrackList(editText: String?){
        if(editText.isNullOrEmpty()){
            renderState(TrackState.HistroryContent(historyTrack =trackHistory))
        }
    }

    fun clearInputEditText(){
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
        renderState(TrackState.HistroryContent(historyTrack =trackHistory))


    }
    private fun writeTrackHistory(){
        interactor.writeTrack(trackHistory)
    }
    fun removeTrackHistory(){
        trackHistory.clear()
        writeTrackHistory()
    }
    fun setOnFocus(editText:String?, hasFocus:Boolean){
        if(hasFocus && editText.isNullOrEmpty()  && trackHistory.isNotEmpty()) {
            renderState(TrackState.HistroryContent(historyTrack = trackHistory))


        }

    }




    private fun searchTrack(newSearchText:String) {
        if (newSearchText.isNotEmpty()) {
            renderState(TrackState.Loading)

            interactor.searchTrack(newSearchText, object :TrackSearchInteractor.TrackConsumer{
                override fun consume(foundTracks: List<Track>?, errorMessage:String?) {


                        if (foundTracks != null){
                            tracks.addAll(foundTracks)

                        }
                    when{
                        errorMessage !=null->{
                            renderState(TrackState.Error(
                                errorMessage = getApplication<Application>().getString(R.string.no_connection)
                            ))
                        }
                        tracks.isEmpty()->{
                            renderState(TrackState.Empty(
                                message = getApplication<Application>().getString(R.string.nothing_found)
                            ))

                        }
                        else->{
                            renderState(TrackState.TrackContent(tracks = tracks))


                        }
                    }


                }
            })
        }

    }
}