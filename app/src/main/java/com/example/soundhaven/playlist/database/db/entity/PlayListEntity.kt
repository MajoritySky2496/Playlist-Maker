package com.example.soundhaven.playlist.database.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist_table")
data class PlayListEntity(
    @PrimaryKey(autoGenerate = true)
    val playListId:Int?,
    val name:String?,
    val description:String?,
    val image:String?,
    val idTracks:String?,
    val numberTracks:String?
)




