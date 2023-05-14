package com.example.playlistmaker.playlist.search.presentation


import android.app.Application
import android.content.SharedPreferences

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.playlistmaker.playlist.creator.Creator
import com.example.playlistmaker.playlist.search.domain.api.TrackSearchInteractor
import com.example.playlistmaker.playlist.search.domain.models.Track


class TracksSearchViewModel(application: Application): AndroidViewModel(application){
    private val interactor = Creator.provideTracksInteractor(getApplication<Application>())
    companion object {
        const val PRODUCT_AMOUNT = "PRODUCT_AMOUNT"
        const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
    private var trackHistory = ArrayList<Track>()
    private val _historyListLiveData = MutableLiveData<List<Track>>()
    val historyListLiveData: LiveData<List<Track>> = _historyListLiveData

    init{
        trackHistory.addAll(interactor.getTrack())
        _historyListLiveData.postValue(trackHistory)
    }
    fun writeHistory(){
        interactor.writeTrack(trackHistory)
        _historyListLiveData.postValue(trackHistory)

    }
    fun clearHistory(){
        trackHistory.clear()
        _historyListLiveData.postValue(trackHistory)
    }


//    private val trackInteractor = Creator.provideTracksInteractor(getApplication<Application>())
//
//    private fun trackAddInHistoryList(track: Track) {
//        val historySet = LinkedHashSet<Track>()
//        trackHistory.add(0, track)
//        if (trackHistory.size > 10) {
//            trackHistory.removeAt(trackHistory.size - 1)
//        }
//        historySet.addAll(trackHistory)
//        trackHistory.clear()
//        trackHistory.addAll(historySet)
//        adapter.notifyDataSetChanged()
//    }
//    private fun searchTrack() {
//        if (inputEditText.text.isNotEmpty()) {
//            progressBar.visibility = View.VISIBLE
//            trackInteractor.searchTrack(inputEditText.text.toString(), object : TrackInteractor.TrackConsumer{
//                override fun consume(foundTracks: List<Track>?, errorMessage:String?) {
//                    handler?.post {
//                        if (foundTracks != null){
//                            progressBar.visibility = View.GONE
//                            track.clear()
//                            adapter.deleteList(track, adapter)
//                            track.addAll(foundTracks)
//                            recyclerView.visibility = View.VISIBLE
//                        }
//                        if (errorMessage !=null){
//                            showMessage(
//                                getString(R.string.no_connection)
//                            )
//                            placeHolderNothingFound.visibility = View.INVISIBLE
//                            noConnectionLayout.visibility = View.VISIBLE
//                            progressBar.visibility = View.GONE
//                        }else if(track.isEmpty()){
//                            showMessage(
//                                getString(R.string.nothing_found)
//                            )
//                            placeHolderNothingFound.visibility = View.VISIBLE
//                            noConnectionLayout.visibility = View.GONE
//                        }
//
//                    }
//                }
//
//            })
//
//        }
//    }
//    private fun searchRequest() {
//        if (inputEditText.text.isEmpty()) {
//            adapter.track = trackHistory
//        } else {
//            searchTrack()
//            adapter.track = track
//            adapter.notifyDataSetChanged()
//        }
//    }
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putString(PRODUCT_AMOUNT, inputEditText.text.toString())
//    }
//
//
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//
//        val text = savedInstanceState.getString(PRODUCT_AMOUNT)
//        if (!text.isNullOrEmpty()) {
//            inputEditText.setText(text)
//            searchTrack()
//
//        }
//    }
//    private fun searchDebounce() {
//        handler?.removeCallbacks(searchRunnable)
//        handler?.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
//    }

    }