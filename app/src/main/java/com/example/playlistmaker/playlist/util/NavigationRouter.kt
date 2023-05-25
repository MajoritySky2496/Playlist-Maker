package com.example.playlistmaker.playlist.util

import android.app.Activity
import android.content.Intent
import com.example.playlistmaker.playlist.player.ui.PlayerActivity
import com.example.playlistmaker.playlist.search.domain.models.Track

class NavigationRouter() {

    fun openActivity(track: Track, activity: Activity ){
        val intent = Intent(activity, PlayerActivity::class.java)
        intent.putExtra(Track::class.java.simpleName, track)
        activity.startActivity(intent)
    }

    fun goBack(activity: Activity){
        activity.finish()
    }
}