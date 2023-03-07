package com.example.playlistmaker

import android.widget.ImageView

data class Track (
    val trackId:String,
    val artistName:String,
    val trackName:String,
    val trackTimeMillis: Int,
    val artworkUrl100:String,
    val collectionName:String,
    val releaseDate:String,
    val primaryGenreName:String,
    val country:String


        )