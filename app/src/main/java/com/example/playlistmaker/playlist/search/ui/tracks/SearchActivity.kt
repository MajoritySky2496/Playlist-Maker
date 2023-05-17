package com.example.playlistmaker.playlist.search.ui.tracks

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.creator.Creator
import com.example.playlistmaker.playlist.player.ui.PlayerActivity
import com.example.playlistmaker.playlist.search.domain.api.TrackSearchInteractor
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.example.playlistmaker.playlist.search.presentation.TracksSearchViewModel
import com.example.playlistmaker.playlist.search.ui.tracks.models.TrackState
import com.google.android.material.internal.ViewUtils.hideKeyboard

import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashSet
import kotlinx.android.synthetic.main.activity_search.progressBar

class SearchActivity : ComponentActivity() {
    private val adapter = TrackAdapter {
    }
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
    lateinit var viewModel:TracksSearchViewModel

    @SuppressLint("RestrictedApi", "CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        viewModel = ViewModelProvider(this)[TracksSearchViewModel::class.java]
        viewModel.observeState().observe(this){render(it)     }

        initViews()
        listener()
        editTextRequestFocus()
    }
    private fun showTrackList(track: List<Track>){
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        adapter.track = track.toMutableList()
        history.visibility = View.GONE
        removeButton.visibility = View.GONE
        adapter.notifyDataSetChanged()
    }
    private fun showHistory(historyTrack:List<Track>){
        if(inputEditText.text.isEmpty() && historyTrack.isNotEmpty() && inputEditText.hasFocus()){
            recyclerView.visibility = View.VISIBLE
            history.visibility = View.VISIBLE
            removeButton.visibility = View.VISIBLE
            adapter.track.clear()
            adapter.track.addAll(historyTrack.toMutableList())
            adapter.notifyDataSetChanged()
        }
    }
    private fun showError(errorMessage: String){
        placeHolderMessage.text = errorMessage
        placeHolderNothingFound.visibility = View.INVISIBLE
        noConnectionLayout.visibility = View.VISIBLE
        placeHolderMessage.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        history.visibility = View.GONE
        removeButton.visibility = View.GONE
        progressBar.visibility = View.GONE
    }
    private fun showEmpty(emptyMessage:String){
        placeHolderMessage.text = emptyMessage
        placeHolderNothingFound.visibility = View.VISIBLE
        noConnectionLayout.visibility = View.GONE
        placeHolderMessage.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        history.visibility = View.GONE
        removeButton.visibility = View.GONE
    }

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

    private fun removeHistory(){
        viewModel.removeTrackHistory()
        recyclerView.visibility = View.GONE
        history.visibility = View.GONE
        removeButton.visibility = View.GONE
        adapter.notifyDataSetChanged()
    }
    private fun refresh(s: CharSequence?){
        viewModel.refreshSearchTrack(s?.toString() ?: "")
        placeHolderMessage.visibility = View.GONE
        noConnectionLayout.visibility = View.GONE
        placeHolderNothingFound.visibility = View.INVISIBLE
    }
    private fun editTextRequestFocus(){
        inputEditText.postDelayed({ inputEditText.requestFocus() }, 500)
    }

    @SuppressLint("RestrictedApi")
    private fun hideKeyBoard(){
        hideKeyboard(currentFocus ?: View(this))

    }
    private fun showLoading(){
        progressBar.visibility = View.VISIBLE
    }
    fun render(state: TrackState){
        when(state){
            is TrackState.Loading -> showLoading()
            is TrackState.TrackContent -> showTrackList(state.tracks)
            is TrackState.HistroryContent -> showHistory(state.historyTrack)
            is TrackState.Error-> showError(state.errorMessage)
            is TrackState.Empty-> showEmpty(state.message)

        }
    }

    private fun clearInputEditText(){
        inputEditText.setText("")
        viewModel.clearInputEditText()
        editTextRequestFocus()
        progressBar.visibility = View.GONE
        placeHolderMessage.visibility = View.GONE
        noConnectionLayout.visibility = View.GONE
        placeHolderNothingFound.visibility = View.INVISIBLE
        hideKeyBoard()
        adapter.notifyDataSetChanged()
    }

    @SuppressLint("RestrictedApi", "CommitPrefEdits")
    private fun listener() {

        inputEditText.setOnFocusChangeListener { view, hasFocus ->
            viewModel.setOnFocus(inputEditText.text.toString(), hasFocus)
        }
        inputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                clearButton.visibility = clearButtonVisibility(s)
                viewModel.onSearchTextChanged(inputEditText.text.toString())
                recyclerView.visibility = View.GONE
                history.visibility = View.GONE
                removeButton.visibility = View.GONE
            }
            override fun afterTextChanged(s: Editable?) {
                placeHolderNothingFound.visibility = View.GONE
                placeHolderMessage.visibility = View.GONE
                noConnectionLayout.visibility = View.GONE
            }
        })

        refreshButton.setOnClickListener {
            refresh(inputEditText.text)
        }
        removeButton.setOnClickListener {
            removeHistory()
        }
        clearButton.setOnClickListener {
            clearInputEditText()
        }
        backButton.setOnClickListener {
            finish()
        }
        adapter.onItemClick = {
            viewModel.trackAddInHistoryList(it)
            viewModel.loadTrackList(inputEditText.text.toString())
            adapter.notifyDataSetChanged()
            val intent = Intent(this, PlayerActivity::class.java)
            intent.putExtra(Track::class.java.simpleName, it)
            startActivity(intent)
        }
    }
}
