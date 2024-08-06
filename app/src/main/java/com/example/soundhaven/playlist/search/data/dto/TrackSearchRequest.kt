package com.example.soundhaven.playlist.search.data.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TrackSearchRequest(
    val expression:String
) :Parcelable