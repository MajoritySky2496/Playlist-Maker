package com.example.playlistmaker.playlist.util

import android.app.Activity
import android.content.Intent
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.playlistmaker.playlist.player.ui.PlayerActivity
import com.example.playlistmaker.playlist.search.domain.models.Track

class NavigationRouter() {
    companion object{
        const val REQUEST_IS_FAVORITE = 0
    }

    fun openActivity(track: Track, activity: Activity ){
        val intent = Intent(activity, PlayerActivity::class.java)
        intent.putExtra(Track::class.java.simpleName, track)
        activity.startActivity(intent)
    }
    fun openActivityFirResult(track: Track, activity: Activity){
        val intent = Intent(activity, PlayerActivity::class.java)
        intent.putExtra(Track::class.java.simpleName, track)

        activity.startActivityForResult(intent, REQUEST_IS_FAVORITE )

    }

    fun goBack(activity: Activity){
        activity.finish()
    }
}