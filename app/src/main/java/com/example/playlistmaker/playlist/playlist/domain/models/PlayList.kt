package com.example.playlistmaker.playlist.playlist.domain.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlayList(
    val playListId:Int?,
    var name:String?,
    var description:String?,
    var image: String?,
    val idTracks:String?,
    val numberTracks:String?
):Parcelable
