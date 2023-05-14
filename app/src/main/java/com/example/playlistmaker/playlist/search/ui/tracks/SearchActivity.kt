package com.example.playlistmaker.playlist.search.ui.tracks

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.creator.Creator
import com.example.playlistmaker.playlist.player.ui.PlayerActivity
import com.example.playlistmaker.playlist.search.data.localwork.SharedPrefsStorage.Companion.HISTORY_TRACK_KEY
import com.example.playlistmaker.playlist.search.data.localwork.SharedPrefsStorage.Companion.PRACTICUM_EXAMPLE_PREFERENCES
import com.example.playlistmaker.playlist.search.domain.api.TrackSearchInteractor
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.example.playlistmaker.playlist.search.presentation.TracksSearchViewModel
import com.example.playlistmaker.playlist.search.presentation.TracksSearchViewModel.Companion.PRODUCT_AMOUNT
import com.example.playlistmaker.playlist.search.presentation.TracksSearchViewModel.Companion.SEARCH_DEBOUNCE_DELAY
import com.google.android.material.internal.ViewUtils.hideKeyboard

import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashSet
import kotlinx.android.synthetic.main.activity_search.progressBar

class SearchActivity : AppCompatActivity() {






    private val searchRunnable = Runnable { searchRequest() }



    private val track = ArrayList<Track>()
    private var trackHistory = ArrayList<Track>()
    lateinit var sharedPrefrs: SharedPreferences
    lateinit var trackSearchInteractor: TrackSearchInteractor



    private val adapter = TrackAdapter {
    }

    private var handler = Handler(Looper.getMainLooper())

    private lateinit var inputEditText: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var placeHolderMessage: TextView
    private lateinit var placeHolderNoConnection: ImageView
    private lateinit var placeHolderNothingFound: ImageView
    private lateinit var refreshButton: ImageView
    lateinit var removeButton: ImageView
    lateinit var history: TextView
    lateinit var noConnectionLayout: FrameLayout
    lateinit var clearButton: ImageView
    lateinit var backButton: ImageView
    lateinit var trackHistoryLinear: LinearLayout
//    lateinit var viewModel:TracksSearchViewModel



    @SuppressLint("RestrictedApi", "CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

//         viewModel = ViewModelProvider(this)[TracksSearchViewModel::class.java]
//         viewModel.historyListLiveData.observe(this){
//       }



        trackSearchInteractor = Creator.provideTracksInteractor(this)
        initViews()
        listener()
        showHistory()
        editTextRequestFocus()



    }

    private fun trackAddInHistoryList(track: Track) {
        val historySet = LinkedHashSet<Track>()
        trackHistory.add(0, track)
        if (trackHistory.size > 10) {
            trackHistory.removeAt(trackHistory.size - 1)
        }
        historySet.addAll(trackHistory)
        trackHistory.clear()
        trackHistory.addAll(historySet)
        adapter.notifyDataSetChanged()
    }
    //удалить
    private fun searchTrack() {
        if (inputEditText.text.isNotEmpty()) {
            showLoading()
            trackSearchInteractor.searchTrack(inputEditText.text.toString(), object :TrackSearchInteractor.TrackConsumer{
                override fun consume(foundTracks: List<Track>?, errorMessage:String?) {
                    handler?.post {
                        if (foundTracks != null){
                            showTrackList(track)
                            track.addAll(foundTracks)
                        }
                        if (errorMessage !=null){
                            showError()
                        }else if(track.isEmpty()){
                            showEmpty()
                        }
                    }
                }
            })
        }
    }
    private fun showTrackList(track: ArrayList<Track>){
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        adapter.deleteList(track, adapter)
    }
    private fun showHistory(){
        trackHistory.addAll(trackSearchInteractor.getTrack())
        adapter.track = trackHistory
        adapter.deleteList(track, adapter)
    }
    private fun showError(){
        showMessage(
            getString(R.string.no_connection)
        )
        placeHolderNothingFound.visibility = View.INVISIBLE
        noConnectionLayout.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }
    private fun showEmpty(){
        showMessage(
            getString(R.string.nothing_found)
        )
        placeHolderNothingFound.visibility = View.VISIBLE
        noConnectionLayout.visibility = View.GONE
    }
    //удалить

    private fun showMessage(text: String) {
        if (text.isNotEmpty()) {
            placeHolderMessage.visibility = View.VISIBLE
            adapter.deleteList(track, adapter)
            placeHolderMessage.text = text
        } else {
            placeHolderMessage.visibility = View.INVISIBLE
        }
    }

    private fun searchRequest() {
        if (inputEditText.text.isEmpty()) {
            adapter.track = trackHistory
        } else {
            searchTrack()
            adapter.track = track
            adapter.notifyDataSetChanged()
        }
    }
    //удалить
    private fun initViews() {
        clearButton = findViewById(R.id.clearIcon)
        backButton = findViewById(R.id.back_button)
        recyclerView = findViewById(R.id.recyclerView_history)
        inputEditText = findViewById(R.id.inputEditText)
        placeHolderMessage = findViewById(R.id.placeholderMessage)
        placeHolderNoConnection = findViewById(R.id.placehoderNoConnection)
        placeHolderNothingFound = findViewById(R.id.placeholderNothingFound)
        refreshButton = findViewById(R.id.refresh)
        removeButton = findViewById(R.id.remove_button)
        history = findViewById(R.id.history)
        trackHistoryLinear = findViewById(R.id.trackHistory)
        noConnectionLayout = findViewById(R.id.noConnectionLayout)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun searchDebounce() {
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }
    private fun removeHistory(){
        recyclerView.visibility = View.GONE
        history.visibility = View.GONE
        removeButton.visibility = View.GONE
        trackHistory.clear()
        trackSearchInteractor.writeTrack(trackHistory)
        adapter.deleteList(trackHistory, adapter)
    }
    private fun refresh(){
        searchDebounce()
        placeHolderMessage.visibility = View.GONE
        noConnectionLayout.visibility = View.GONE
        placeHolderNothingFound.visibility = View.INVISIBLE
    }
    private fun editTextRequestFocus(){
        inputEditText.postDelayed({ inputEditText.requestFocus() }, 500)
    }
    private fun writeTrackHistory(){
        trackSearchInteractor.writeTrack(trackHistory)
    }
    private fun setOnFocus(view: View, hasFocus:Boolean){
        removeButton.visibility =
            if (hasFocus && inputEditText.text.isEmpty() && trackHistory.isNotEmpty()) View.VISIBLE else View.GONE
        recyclerView.visibility =
            if (hasFocus && inputEditText.text.isEmpty() && trackHistory.isNotEmpty()) View.VISIBLE else View.GONE
        history.visibility =
            if (hasFocus && inputEditText.text.isEmpty() && trackHistory.isNotEmpty()) View.VISIBLE else View.GONE
    }
    @SuppressLint("RestrictedApi")
    private fun hideKeyBoard(){
        hideKeyboard(currentFocus ?: View(this))
    }
    private fun showLoading(){
        progressBar.visibility = View.VISIBLE
    }

    //удалить

    @SuppressLint("RestrictedApi", "CommitPrefEdits")
    private fun listener() {

        inputEditText.setOnFocusChangeListener { view, hasFocus ->
            setOnFocus(view, hasFocus)
        }
        inputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                removeButton.visibility =
                    if (inputEditText.hasFocus() && p0?.isEmpty() == true) View.VISIBLE else View.GONE
                recyclerView.visibility =
                    if (inputEditText.hasFocus() && p0?.isEmpty() == true) View.VISIBLE else View.GONE
                history.visibility =
                    if (inputEditText.hasFocus() && p0?.isEmpty() == true) View.VISIBLE else View.GONE
                clearButton.visibility = clearButtonVisibility(p0)
            }
            override fun afterTextChanged(p0: Editable?) {
                placeHolderNothingFound.visibility = View.GONE
                placeHolderMessage.visibility = View.GONE
                noConnectionLayout.visibility = View.GONE
                searchDebounce()
            }
        })

        refreshButton.setOnClickListener {
            refresh()
        }
        removeButton.setOnClickListener {
            removeHistory()
        }
        clearButton.setOnClickListener {
            handler.removeCallbacks(searchRunnable)
            progressBar.visibility = View.GONE
            editTextRequestFocus()
            writeTrackHistory()
            inputEditText.setText("")
            placeHolderMessage.visibility = View.GONE
            noConnectionLayout.visibility = View.GONE
            placeHolderNothingFound.visibility = View.INVISIBLE
            track.clear()
            adapter.track = trackHistory
            adapter.deleteList(track, adapter)
            hideKeyBoard()
            inputEditText.clearFocus()
        }
        backButton.setOnClickListener {
            finish()
        }
        adapter.onItemClick = {
            trackAddInHistoryList(it)
            trackSearchInteractor.writeTrack(trackHistory)
            val intent = Intent(this, PlayerActivity::class.java)
            intent.putExtra(Track::class.java.simpleName, it)
            startActivity(intent)
        }

    }






}
