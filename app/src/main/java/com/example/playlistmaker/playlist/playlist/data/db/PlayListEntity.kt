package com.example.playlistmaker.playlist.playlist.data.db

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.playlistmaker.playlist.mediateca.data.db.TrackEntity
import com.google.gson.Gson

@Entity(tableName = "playlist_table")
data class PlayListEntity(
    @PrimaryKey
    val playListId:Int?,
    val name:String?,
    val description:String?,
    val image:String?,
    val idTracks:String?,
    val numberTracks:String?
)




