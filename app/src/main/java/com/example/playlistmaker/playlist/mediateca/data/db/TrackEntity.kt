package com.example.playlistmaker.playlist.mediateca.data.db

import android.provider.Telephony.Mms.Part.TEXT
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "track_table")
data class TrackEntity(
    @PrimaryKey
    val trackId: String,
    val artistName: String,
    val trackName: String,
    val releaseDate: String,
    val primaryGenreName: String,
    val country: String,
    val collectionName: String,
    val artworkUrl100: String,
    val trackTimeMillis: Int,
    val previewUrl: String?,
    
)


