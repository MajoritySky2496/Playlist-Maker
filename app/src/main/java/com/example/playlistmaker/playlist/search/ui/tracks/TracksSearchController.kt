package com.example.playlistmaker.playlist.search.ui.tracks

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.playlistmaker.R
import com.example.playlistmaker.SearchHistory
import com.example.playlistmaker.playlist.creator.Creator
import com.example.playlistmaker.playlist.player.ui.PlayerActivity
import com.example.playlistmaker.playlist.search.domain.api.TrackInteractor
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.google.android.material.internal.ViewUtils
import kotlinx.android.synthetic.main.activity_search.progressBar

class TracksSearchController(private val activity: Activity, private val adapter: TrackAdapter,
                             private val sharedPrefs: SharedPreferences) {


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

    fun OnCreate(){
        initViews()
        val searchHistory = SearchHistory(sharedPrefs)




    }
    private fun initViews() {
        clearButton = activity.findViewById(R.id.clearIcon)
        backButton = activity.findViewById(R.id.back_button)
        recyclerView = activity.findViewById(R.id.recyclerView_history)
        inputEditText = activity.findViewById(R.id.inputEditText)
        placeHolderMessage = activity.findViewById(R.id.placeholderMessage)
        placeHolderNoConnection = activity.findViewById(R.id.placehoderNoConnection)
        placeHolderNothingFound = activity.findViewById(R.id.placeholderNothingFound)
        refreshButton = activity.findViewById(R.id.refresh)
        removeButton = activity.findViewById(R.id.remove_button)
        history = activity.findViewById(R.id.history)
        trackHistoryLinear = activity.findViewById(R.id.trackHistory)
        noConnectionLayout = activity.findViewById(R.id.noConnectionLayout)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }





    }