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

const val PRACTICUM_EXAMPLE_PREFERENCES = "practicum_example_preferences"

class SearchHistory: Application() {
    private val historyTrack = ArrayList<Track>()

    fun getHistory(sharedPreferences: SharedPreferences):ArrayList<Track>{
        val trackList = ArrayList<Track>()
        for (track in sharedPreferences.all){
            val savedTrack = Gson().fromJson(track.value.toString(), Track::class.java)
            trackList.add(savedTrack)
            if (trackList.size >9){
                trackList.removeAt(10)
            }
        }
        return trackList
    }


  fun getHistoryTrack(track: Track):ArrayList<Track>{
       val historyTrack = ArrayList<Track>()
      historyTrack.add(track)
      return historyTrack

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