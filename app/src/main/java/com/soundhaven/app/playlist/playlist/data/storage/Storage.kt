package com.soundhaven.app.playlist.playlist.data.storage

import android.net.Uri

interface Storage {
    suspend fun saveImageToPrivateStorage(uri: Uri?)
    suspend fun getImage(id:String):Uri
}