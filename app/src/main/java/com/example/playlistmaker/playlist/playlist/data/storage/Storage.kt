package com.example.playlistmaker.playlist.playlist.data.storage

import android.net.Uri

interface Storage {
    fun saveImageToPrivateStorage(uri: Uri, id:String)
    fun getImage(id:String):Uri
}