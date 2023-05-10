package com.example.playlistmaker.playlist.search.data.localwork

import android.content.SharedPreferences
import com.example.playlistmaker.playlist.search.data.TrackStorage
import com.example.playlistmaker.playlist.search.data.dto.TrackDto
import com.example.playlistmaker.playlist.search.data.dto.TrackWriteResponce
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.google.gson.Gson

class SharedPrefsStorage(val sharedPrefrs: SharedPreferences): TrackStorage {
    companion object {
        const val PRACTICUM_EXAMPLE_PREFERENCES = "practicum_example_preferences"
        const val HISTORY_TRACK_KEY = "HISTORY_TRACK_KEY"
    }
    override fun doRequest(): Array<Track> {



            val json = sharedPrefrs.getString(HISTORY_TRACK_KEY, null) ?: return emptyArray()
            return Gson().fromJson(json, Array<Track>::class.java)



    }

    override fun doWrite(historyTrack: List<Track>) {
        val json = Gson().toJson(historyTrack)
        sharedPrefrs.edit().putString(HISTORY_TRACK_KEY, json).apply()
    }
}