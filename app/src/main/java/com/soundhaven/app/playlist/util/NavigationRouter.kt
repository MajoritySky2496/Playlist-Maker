package com.soundhaven.app.playlist.util

import android.app.Activity

class NavigationRouter() {
    companion object{
        const val REQUEST_IS_FAVORITE = 0
    }

    fun goBack(activity: Activity){
        activity.finish()
    }
}