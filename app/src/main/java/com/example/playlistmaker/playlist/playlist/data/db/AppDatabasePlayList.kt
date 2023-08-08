package com.example.playlistmaker.playlist.playlist.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playlistmaker.playlist.mediateca.data.db.TrackEntity

@Database(version = 8, entities = [PlayListEntity::class, TrackEntity::class])
abstract class AppDatabasePlayList: RoomDatabase() {
    abstract fun playListDao(): PlayListDao
    abstract fun tracksDao():PlayListTrackDao
}