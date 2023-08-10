package com.example.playlistmaker.playlist.playlist.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playlistmaker.playlist.mediateca.data.db.TrackEntity
@Dao
interface PlayListTrackDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrackPlayList(trackEntity: TrackEntity)

    @Query("SELECT *  FROM track_table  WHERE trackId = :trackId")
    suspend fun getTrack(trackId:String?):TrackEntity
}