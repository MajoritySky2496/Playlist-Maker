package com.example.playlistmaker.playlist.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playlistmaker.playlist.database.db.dao.TrackDao
import com.example.playlistmaker.playlist.database.db.entity.TrackEntity
import com.example.playlistmaker.playlist.database.db.dao.PlayListDao
import com.example.playlistmaker.playlist.database.db.entity.PlayListEntity
import com.example.playlistmaker.playlist.database.db.dao.PlayListTrackDao
import com.example.playlistmaker.playlist.database.db.entity.PlayListTrackEntity

@Database(version = 4, entities = [TrackEntity::class, PlayListEntity::class, PlayListTrackEntity::class ])
abstract class AppDatabase:RoomDatabase() {
    abstract fun trackDao(): TrackDao
    abstract fun playListDao(): PlayListDao
    abstract fun tracksDao(): PlayListTrackDao
}