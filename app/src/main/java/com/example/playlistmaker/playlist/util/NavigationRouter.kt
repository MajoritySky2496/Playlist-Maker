package com.example.playlistmaker.playlist.util

import android.app.Activity
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.playlistmaker.playlist.player.ui.PlayerActivity
import com.example.playlistmaker.playlist.player.ui.TrackPlayerActivity
import com.example.playlistmaker.playlist.search.domain.models.Track

class NavigationRouter() {

    fun openActivity(track: Track, activity: Activity ){
        val intent = Intent(activity, TrackPlayerActivity::class.java)
        intent.putExtra(Track::class.java.simpleName, track)
        activity.startActivity(intent)
    }
    fun getTruck(intent: Intent): Track {
        return intent.getParcelableExtra<Track>(Track::class.java.simpleName) as Track

    }
    fun goBack(activity: Activity){
        activity.finish()
    }
}