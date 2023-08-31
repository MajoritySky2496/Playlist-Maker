package com.example.playlistmaker.playlist.database.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "playlist_track_table")
data class PlayListTrackEntity(
        @PrimaryKey
        val trackId: String,
        val artistName: String,
        val trackName: String,
        val releaseDate: String,
        val primaryGenreName: String,
        val country: String,
        val collectionName: String,
        val artworkUrl100: String,
        val artworkUrl60: String,
        val trackTimeMillis: Int,
        val previewUrl: String?,
    )
