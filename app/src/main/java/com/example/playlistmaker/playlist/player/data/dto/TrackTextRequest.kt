package com.example.playlistmaker.playlist.player.data.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TrackTextRequestDto(
    val artist: String,
    val title: String
) : Parcelable
