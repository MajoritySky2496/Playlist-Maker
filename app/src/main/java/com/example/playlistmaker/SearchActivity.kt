package com.example.playlistmaker

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class SearchActivity : AppCompatActivity() {

    private val itunesUrlBase = "https://itunes.apple.com"

    companion object {
        const val PRODUCT_AMOUNT = "PRODUCT_AMOUNT"
    }

    private var retrofit = Retrofit.Builder().baseUrl(itunesUrlBase)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val trackService = retrofit.create(ItunesApi::class.java)
    private val track = ArrayList<Track>()
    private var trackHistory = ArrayList<Track>()


    private val adapter = TrackAdapter{
        trackHistory.add(it)

    }
    private val adapterHistory = TrackAdapter{

    }


    lateinit var inputEditText: EditText
    lateinit var recyclerView_history: RecyclerView
    lateinit var recyclerView: RecyclerView
    private lateinit var placeHolderMessage: TextView
    private lateinit var placeHolderNoConnection: ImageView
    private lateinit var placeHolderNothingFound: ImageView
    private lateinit var refreshButton: ImageView
    lateinit var removeButton: ImageView
    lateinit var history: TextView


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val clearButton = findViewById<ImageView>(R.id.clearIcon)
        val backButton = findViewById<ImageView>(R.id.back_button)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView_history = findViewById(R.id.recyclerView_history)
        inputEditText = findViewById(R.id.inputEditText)
        placeHolderMessage = findViewById(R.id.placeholderMessage)
        placeHolderNoConnection = findViewById(R.id.placehoderNoConnection)
        placeHolderNothingFound = findViewById(R.id.placeholderNothingFound)
        refreshButton = findViewById(R.id.refresh)
        removeButton = findViewById(R.id.remove_button)
        history = findViewById(R.id.history)
        val sharedPrefrs = getSharedPreferences(PRACTICUM_EXAMPLE_PREFERENCES, MODE_PRIVATE)



        adapter.track = track
        recyclerView.adapter = adapter
        recyclerView_history.adapter = adapterHistory
        recyclerView_history.layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val searchHistory = SearchHistory(sharedPrefrs)

        searchHistory.write( trackHistory)
        searchHistory.onFocus(inputEditText, recyclerView_history, removeButton, history)
        var trackHistoryFromPrefrs = ArrayList<Track>()
        trackHistoryFromPrefrs.addAll(searchHistory.getHistory())
        adapterHistory.track = trackHistoryFromPrefrs


        clearButton.setOnClickListener {
            inputEditText.setText("")
            placeHolderNoConnection.visibility = View.INVISIBLE
            placeHolderNothingFound.visibility = View.INVISIBLE
            placeHolderMessage.visibility = View.INVISIBLE
            recyclerView.visibility = View.INVISIBLE
            refreshButton.visibility = View.INVISIBLE
            track.clear()
            hideKeyboard(currentFocus ?: View(this))
        }
        backButton.setOnClickListener {
            finish()
        }
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, after: Int) {
                clearButton.visibility = clearButtonVisibility(s)

            }

            override fun afterTextChanged(s: Editable?) {
            }

        }
        inputEditText.addTextChangedListener(simpleTextWatcher)
        refreshButton.setOnClickListener {
            searchTrack()
            placeHolderNoConnection.visibility = View.INVISIBLE
            placeHolderNothingFound.visibility = View.INVISIBLE
            placeHolderMessage.visibility = View.INVISIBLE
            refreshButton.visibility = View.INVISIBLE

        }


        inputEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchTrack()
                true
            }
            false
        }

    }

    private fun searchTrack() {
        if (inputEditText.text.isNotEmpty()) {

            trackService.search(inputEditText.text.toString())
                .enqueue(object : Callback<TrackResponce> {
                    override fun onResponse(
                        call: Call<TrackResponce>,
                        response: Response<TrackResponce>
                    ) {
                        if (response.code() == 200) {

                            if (response.body()?.results?.isNotEmpty() == true) {
                                adapter.deleteList(track, adapter)
                                track.addAll(response.body()?.results!!)
                                recyclerView.visibility = View.VISIBLE


                            }
                            if (track.isEmpty()) {
                                showMessage(
                                    getString(R.string.nothing_found),
                                    response.code().toString()
                                )
                                placeHolderNothingFound.visibility = View.VISIBLE
                                placeHolderNoConnection.visibility = View.INVISIBLE


                            }

                        } else {
                            showMessage(
                                getString(R.string.no_connection),
                                response.code().toString()
                            )
                            placeHolderNothingFound.visibility = View.INVISIBLE
                            placeHolderNoConnection.visibility = View.VISIBLE
                            refreshButton.visibility = View.VISIBLE

                        }
                    }

                    override fun onFailure(call: Call<TrackResponce>, t: Throwable) {
                        showMessage(getString(R.string.no_connection), t.message.toString())
                        placeHolderNothingFound.visibility = View.INVISIBLE
                        placeHolderNoConnection.visibility = View.VISIBLE
                        refreshButton.visibility = View.VISIBLE
                    }
                })
        }

    }

    private fun showMessage(text: String, additionalMessage: String) {
        if (text.isNotEmpty()) {
            placeHolderMessage.visibility = View.VISIBLE
            adapter.deleteList(track, adapter)
            placeHolderMessage.text = text

        } else {
            placeHolderMessage.visibility = View.INVISIBLE
        }
    }


    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(PRODUCT_AMOUNT, inputEditText.text.toString())

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val text = savedInstanceState.getString(PRODUCT_AMOUNT)
        if (!text.isNullOrEmpty()) {
            inputEditText.setText(text)
            searchTrack()

        }

    }


}
