package com.example.playlistmaker.playlist.mediateca.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track:TrackEntity)

    @Delete
    suspend fun deleteTrack(track:TrackEntity)

    @Query("SELECT * FROM track_table")
    suspend fun getTracks():List<TrackEntity>?

    @Query("SELECT * FROM track_table WHERE trackId =:id")
    suspend fun getTrackId(id:String):TrackEntity


}