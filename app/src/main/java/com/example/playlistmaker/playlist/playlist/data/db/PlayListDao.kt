package com.example.playlistmaker.playlist.playlist.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.playlistmaker.playlist.mediateca.data.db.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayList(playList: PlayListEntity)

    @Delete
    suspend fun deletePlayList(playList: PlayListEntity)

    @Update(entity = PlayListEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun updateMovie(movieEntity: PlayListEntity)

    @Query("SELECT * FROM playlist_table")
    fun getPlayLists(): Flow<List<PlayListEntity>>

    @Query("SELECT *  FROM playlist_table  WHERE playListId = :playListId")
    suspend fun getPlayList(playListId:Long):PlayListEntity


}