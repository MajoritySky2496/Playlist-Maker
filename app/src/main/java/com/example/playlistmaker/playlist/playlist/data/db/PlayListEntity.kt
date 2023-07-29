package com.example.playlistmaker.playlist.playlist.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist_table")
data class PlayListEntity(
    @PrimaryKey(autoGenerate = true)
    val playListId:Long,
    val name:String?,
    val description:String?,
    val image:String?,
    val idTracks:String?,
    val numberTracks:Int?
)
