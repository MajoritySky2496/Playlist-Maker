package com.example.playlistmaker.playlist.playlist.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playlistmaker.playlist.mediateca.data.db.TrackDao
import com.example.playlistmaker.playlist.mediateca.data.db.TrackEntity

@Database(version = 1, entities = [PlayListEntity::class])
abstract class AppDatabase: RoomDatabase() {
    abstract fun playListDao(): PlayListDao
}