package com.example.playlistmaker.playlist.search.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Track(
    val trackId: String,
    val artistName: String,
    val trackName: String,
    val releaseDate: String?,
    val primaryGenreName: String,
    val country: String,
    val collectionName: String,
    val artworkUrl100: String,
    val trackTimeMillis: Int,
    val previewUrl: String?
) : Parcelable
