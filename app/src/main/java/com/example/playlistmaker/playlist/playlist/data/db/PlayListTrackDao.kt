package com.example.playlistmaker.playlist.playlist.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.playlistmaker.playlist.mediateca.data.db.TrackEntity
@Dao
interface PlayListTrackDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrackPlayList(trackEntity: TrackEntity)
}