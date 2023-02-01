package com.example.playlistmaker

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.internal.ViewUtils.hideKeyboard

class SearchActivity : AppCompatActivity() {
    var inputEditText: EditText? = null
    companion object {
        const val PRODUCT_AMOUNT = "PRODUCT_AMOUNT"
 }
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val clearButton = findViewById<ImageView>(R.id.clearIcon)
        val backButton = findViewById<ImageView>(R.id.back_button)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val image = findViewById<ImageView>(R.id.image_url)

        inputEditText = findViewById<EditText>(R.id.inputEditText)
        clearButton.setOnClickListener{
            inputEditText?.setText("")
            hideKeyboard(currentFocus ?: View(this))
        }
        backButton.setOnClickListener{
            finish()
        }
        val simpleTextWatcher = object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, after: Int) {
                clearButton.visibility = clearButtonVisibility(s)

            }
            override fun afterTextChanged(s: Editable?) {
            }

        }
        inputEditText?.addTextChangedListener(simpleTextWatcher)


        val track1 = Track("Nirvana",
            "Smells Like Teen Spirit",
            "5:01",
            "https://is5-ssl.mzstatic.com/image/thumb/Music115/v4/7b/58/c2/7b58c21a-2b51-2bb2-e59a-9bb9b96ad8c3/00602567924166.rgb.jpg/100x100bb.jpg")
        val track2 = Track("Michael Jackson",
            "Billie Jean",
            "4:35",
            "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/3d/9d/38/3d9d3811-71f0-3a0e-1ada-3004e56ff852/827969428726.jpg/100x100bb.jpg")
        val track3 = Track("Bee Gees",
            "Stayin' Alive",
            "4:10",
            "https://is4-ssl.mzstatic.com/image/thumb/Music115/v4/1f/80/1f/1f801fc1-8c0f-ea3e-d3e5-387c6619619e/16UMGIM86640.rgb.jpg/100x100bb.jpg")
        val track4 = Track("Led Zeppelin",
            "Whole Lotta Love",
            "5:33",
            "https://is2-ssl.mzstatic.com/image/thumb/Music62/v4/7e/17/e3/7e17e33f-2efa-2a36-e916-7f808576cf6b/mzm.fyigqcbs.jpg/100x100bb.jpg")
        val track5 = Track("Guns N' Roses",
            "Sweet Child O'Mine",
            "5:03",
            "https://is2-ssl.mzstatic.com/image/thumb/Music62/v4/7e/17/e3/7e17e33f-2efa-2a36-e916-7f808576cf6b/mzm.fyigqcbs.jpg/100x100bb.jpg")
        val trackList = listOf<Track>(track1, track2, track3, track4, track5)
        val trackAdapter = TrackAdapter(trackList)
        recyclerView.adapter = trackAdapter


    }


    private fun  clearButtonVisibility(s: CharSequence?): Int{
        return  if (s.isNullOrEmpty()){
            View.GONE
        }else{
           View.VISIBLE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(PRODUCT_AMOUNT,  inputEditText?.text.toString())
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val text = savedInstanceState.getString(PRODUCT_AMOUNT)
        if (!text.isNullOrEmpty()){
            inputEditText?.setText(text)
        }

    }
    }
