package com.example.playlistmaker.playlist.playlist.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 2, entities = [PlayListEntity::class])
abstract class AppDatabasePlayList: RoomDatabase() {
    abstract fun playListDao(): PlayListDao
}