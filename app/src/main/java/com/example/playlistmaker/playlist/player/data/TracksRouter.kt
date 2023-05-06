package com.example.playlistmaker.playlist.player.data

import android.content.Intent
import com.example.playlistmaker.playlist.search.domain.models.Track

class TracksRouter {
    fun getTruck(intent: Intent): Track {
        return intent.getParcelableExtra<Track>(Track::class.java.simpleName) as Track

    }
}