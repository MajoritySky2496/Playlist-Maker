package com.example.playlistmaker.playlist.search.data.localwork

import android.content.SharedPreferences
import com.example.playlistmaker.playlist.search.data.TrackStorage
import com.example.playlistmaker.playlist.search.data.dto.TrackDto
import com.google.gson.Gson

class SharedPrefsStorage(val sharedPrefrs: SharedPreferences) : TrackStorage {
    companion object {
        const val HISTORY_TRACK_KEY = "HISTORY_TRACK_KEY"
        const val FALSE = "false"
    }

    override fun doRequest(): Array<TrackDto> {
        val json = sharedPrefrs.getString(HISTORY_TRACK_KEY, null) ?: return emptyArray()
        return Gson().fromJson(json, Array<TrackDto>::class.java)
    }

    override fun doWrite(historyTrack: List<TrackDto>) {
        val json = Gson().toJson(historyTrack)
        sharedPrefrs.edit().putString(HISTORY_TRACK_KEY, json).apply()
    }
}