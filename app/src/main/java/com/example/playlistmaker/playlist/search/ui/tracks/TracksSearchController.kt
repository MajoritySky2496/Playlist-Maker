package com.example.playlistmaker.playlist.search.ui.tracks

import android.annotation.SuppressLint
import android.app.Activity
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
import com.example.playlistmaker.HISTORY_TRACK_KEY
import com.example.playlistmaker.PRACTICUM_EXAMPLE_PREFERENCES
import com.example.playlistmaker.R
import com.example.playlistmaker.SearchHistory
import com.example.playlistmaker.playlist.creator.Creator
import com.example.playlistmaker.playlist.player.ui.PlayerActivity
import com.example.playlistmaker.playlist.search.domain.api.TrackInteractor
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.google.android.material.internal.ViewUtils
import kotlinx.android.synthetic.main.activity_search.progressBar

class TracksSearchController(private val activity: Activity, private val adapter: TrackAdapter) {
    private val trackInteractor = Creator.provideTracksInteractor(this)


    private val searchRunnable = Runnable { searchRequest() }



    private val track = ArrayList<Track>()
    private var trackHistory = ArrayList<Track>()



    private var handler: Handler? = null
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


    @SuppressLint("RestrictedApi", "CommitPrefEdits")
     fun onCreate() {

        val sharedPrefrs = getSharedPreferences(
            PRACTICUM_EXAMPLE_PREFERENCES,
            AppCompatActivity.MODE_PRIVATE
        )
        initViews()
        val searchHistory = SearchHistory(sharedPrefrs)
        handler = Handler(Looper.getMainLooper())

        listener(sharedPrefrs, searchHistory)
        inputEditText.postDelayed({ inputEditText.requestFocus() }, 500)

        searchHistory.onFocus(
            inputEditText,
            recyclerView,
            history,
            removeButton,
            trackHistory,
        )
        if (inputEditText.text.isEmpty()) {
            trackHistory.addAll(searchHistory.getHistory())
            adapter.track = trackHistory
            adapter.notifyDataSetChanged()
        }
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


    private fun searchTrack() {
        if (inputEditText.text.isNotEmpty()) {
            progressBar.visibility = View.VISIBLE
            trackInteractor.searchTrack(inputEditText.text.toString(), object : TrackInteractor.TrackConsumer{
                override fun consume(foundTracks: List<Track>?, errorMessage:String?) {
                    handler?.post {
                        if (foundTracks != null){
                            progressBar.visibility = View.GONE
                            track.clear()
                            adapter.deleteList(track, adapter)
                            track.addAll(foundTracks)
                            recyclerView.visibility = View.VISIBLE
                        }
                        if (errorMessage !=null){
                            showMessage(
                                getString(R.string.no_connection)
                            )
                            placeHolderNothingFound.visibility = View.INVISIBLE
                            noConnectionLayout.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                        }else if(track.isEmpty()){
                            showMessage(
                                getString(R.string.nothing_found)
                            )
                            placeHolderNothingFound.visibility = View.VISIBLE
                            noConnectionLayout.visibility = View.GONE
                        }

                    }
                }

            })

//                    override fun onFailure(call: Call<TrackResponce>, t: Throwable) {
//                        progressBar.visibility = View.GONE
//                        showMessage(getString(R.string.no_connection), t.message.toString())
//                        placeHolderNothingFound.visibility = View.INVISIBLE
//                        noConnectionLayout.visibility = View.VISIBLE
//                    }
        }
    }

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

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

     fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(PRODUCT_AMOUNT, inputEditText.text.toString())
    }

     fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val text = savedInstanceState.getString(PRODUCT_AMOUNT)
        if (!text.isNullOrEmpty()) {
            inputEditText.setText(text)
            searchTrack()

        }
    }

    private fun searchDebounce() {
        handler?.removeCallbacks(searchRunnable)
        handler?.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    @SuppressLint("RestrictedApi", "CommitPrefEdits")
    private fun listener(sharedPrefrs: SharedPreferences, searchHistory: SearchHistory) {
        inputEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchTrack()
                adapter.track = track
                true
            }
            false
        }
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, after: Int) {
                clearButton.visibility = clearButtonVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {
                inputEditText.doAfterTextChanged {
                    placeHolderNothingFound.visibility = View.GONE
                    placeHolderMessage.visibility = View.GONE
                    noConnectionLayout.visibility = View.GONE
                }
                searchDebounce()
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)
        refreshButton.setOnClickListener {
            searchTrack()
            placeHolderMessage.visibility = View.GONE
            noConnectionLayout.visibility = View.GONE
            placeHolderNothingFound.visibility = View.INVISIBLE
        }
        removeButton.setOnClickListener {
            sharedPrefrs.edit().remove(HISTORY_TRACK_KEY)
            recyclerView.visibility = View.GONE
            history.visibility = View.GONE
            removeButton.visibility = View.GONE
            trackHistory.clear()
            searchHistory.write(trackHistory)
            adapter.notifyDataSetChanged()

        }
        clearButton.setOnClickListener {
            handler?.removeCallbacks(searchRunnable)
            progressBar.visibility = View.GONE
            inputEditText.postDelayed({ inputEditText.requestFocus() }, 500)
            searchHistory.write(trackHistory)
            inputEditText.setText("")
            placeHolderMessage.visibility = View.GONE
            noConnectionLayout.visibility = View.GONE
            placeHolderNothingFound.visibility = View.INVISIBLE
            track.clear()
            adapter.track = trackHistory
            adapter.notifyDataSetChanged()
            ViewUtils.hideKeyboard(currentFocus ?: View(this))
            inputEditText.clearFocus()
        }
        backButton.setOnClickListener {
            finish()
        }
        adapter.onItemClick = {
            trackAddInHistoryList(it)
            searchHistory.write(trackHistory)
            val intent = Intent(this, PlayerActivity::class.java)
            intent.putExtra(Track::class.java.simpleName, it)
            startActivity(intent)
        }

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

    companion object {
        const val PRODUCT_AMOUNT = "PRODUCT_AMOUNT"
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }


}