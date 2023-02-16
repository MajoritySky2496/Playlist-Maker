package com.example.playlistmaker

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
    private val adapter = TrackAdapter()
    lateinit var inputEditText: EditText
    lateinit var recyclerView: RecyclerView
    private lateinit var placeHolderMessage: TextView
    private lateinit var placeHoderNoConnection: ImageView
    private lateinit var placeHolderNothingFound: ImageView
    private lateinit var refreshButton: ImageView


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val clearButton = findViewById<ImageView>(R.id.clearIcon)
        val backButton = findViewById<ImageView>(R.id.back_button)
        recyclerView = findViewById(R.id.recyclerView)
        inputEditText = findViewById(R.id.inputEditText)
        placeHolderMessage = findViewById(R.id.placeholderMessage)
        placeHoderNoConnection = findViewById(R.id.placehoderNoConnection)
        placeHolderNothingFound = findViewById(R.id.placeholderNothingFound)
        refreshButton = findViewById(R.id.refresh)
        adapter.track = track
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        clearButton.setOnClickListener {
            inputEditText.setText("")
            placeHoderNoConnection.visibility = View.INVISIBLE
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
            placeHoderNoConnection.visibility = View.INVISIBLE
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
                            track.clear()
                            if (response.body()?.results?.isNotEmpty() == true) {
                                track.addAll(response.body()?.results!!)
                                recyclerView.visibility = View.VISIBLE
                                adapter.notifyDataSetChanged()


                            }
                            if (track.isEmpty()) {
                                showMessage(
                                    getString(R.string.nothing_found),
                                    response.code().toString()
                                )
                                placeHolderNothingFound.visibility = View.VISIBLE
                                placeHoderNoConnection.visibility = View.INVISIBLE


                            }

                        } else {
                            showMessage(
                                getString(R.string.no_connection),
                                response.code().toString()
                            )
                            placeHolderNothingFound.visibility = View.INVISIBLE
                            placeHoderNoConnection.visibility = View.VISIBLE
                            refreshButton.visibility = View.VISIBLE

                        }
                    }

                    override fun onFailure(call: Call<TrackResponce>, t: Throwable) {
                        showMessage(getString(R.string.no_connection), t.message.toString())
                        placeHolderNothingFound.visibility = View.INVISIBLE
                        placeHoderNoConnection.visibility = View.VISIBLE
                        refreshButton.visibility = View.VISIBLE
                    }
                })
        }

    }

    private fun showMessage(text: String, additionalMessage: String) {
        if (text.isNotEmpty()) {

            track.clear()
            adapter.notifyDataSetChanged()
            placeHolderMessage.text = text

        } else {
            placeHolderMessage.visibility = View.GONE
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
