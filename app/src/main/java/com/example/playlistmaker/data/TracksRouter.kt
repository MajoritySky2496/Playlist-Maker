package com.example.playlistmaker.data

import android.content.Intent
import com.example.playlistmaker.Track

class TracksRouter {
    fun getTruck(intent: Intent):Track{
        return intent.getParcelableExtra<Track>(Track::class.java.simpleName) as Track

    }


}