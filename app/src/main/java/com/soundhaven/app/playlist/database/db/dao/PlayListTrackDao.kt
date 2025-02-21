package com.soundhaven.app.playlist.database.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.soundhaven.app.playlist.database.db.entity.PlayListTrackEntity

@Dao
interface PlayListTrackDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrackPlayList(playListTrackEntity:PlayListTrackEntity)

    @Query("SELECT *  FROM playlist_track_table  WHERE trackId = :trackId")
    suspend fun getTrack(trackId:String?): PlayListTrackEntity

    @Query("SELECT * FROM playlist_track_table")
    suspend fun getTraks():List<PlayListTrackEntity>

    @Delete
    suspend fun deleteTrack(track: PlayListTrackEntity)
}