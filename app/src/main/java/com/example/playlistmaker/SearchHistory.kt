package com.example.playlistmaker

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

const val PRACTICUM_EXAMPLE_PREFERENCES = "practicum_example_preferences"
const val HISTORY_TRACK_KEY = "HISTORY_TRACK_KEY"

class SearchHistory(val sharedPrefrs: SharedPreferences) {


    fun write(historyTrack:ArrayList<Track>){
        val json = Gson().toJson(historyTrack)
        sharedPrefrs.edit().putString(HISTORY_TRACK_KEY, json).apply()
    }


    fun getHistory():Array<Track>{
        val json = sharedPrefrs.getString(HISTORY_TRACK_KEY, null) ?: return emptyArray<Track>()
         return Gson().fromJson(json, Array<Track>::class.java)
    }




    fun onFocus(editText:EditText, recyclerView_history: RecyclerView, removeButton:ImageView, history:TextView){
        editText.setOnFocusChangeListener{ view, hasFocus ->
            recyclerView_history.visibility  = if (hasFocus && editText.text.isEmpty()) View.VISIBLE else View.GONE
            removeButton.visibility = if (hasFocus && editText.text.isEmpty()) View.VISIBLE else View.GONE
            history.visibility = if (hasFocus && editText.text.isEmpty()) View.VISIBLE else View.GONE

        }
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                recyclerView_history.visibility = if (editText.hasFocus() && p0?.isEmpty()==true) View.VISIBLE else View.GONE
                removeButton.visibility = if (editText.hasFocus() && p0?.isEmpty()==true) View.VISIBLE else View.GONE
                history.visibility = if (editText.hasFocus() && p0?.isEmpty()==true) View.VISIBLE else View.GONE
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

}
}