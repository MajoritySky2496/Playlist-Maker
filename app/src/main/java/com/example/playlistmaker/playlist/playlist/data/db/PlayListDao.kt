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

    @Query("DELETE FROM playlist_table ")
    suspend fun PlayListsClear()

    @Query("SELECT * FROM playlist_table WHERE playListId = :playListId")
    fun getPlayList(playListId:Int?):Flow<PlayListEntity>

    @Update(entity = PlayListEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePlayList(playList: PlayListEntity)



}