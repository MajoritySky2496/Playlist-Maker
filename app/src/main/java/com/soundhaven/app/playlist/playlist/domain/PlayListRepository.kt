package com.soundhaven.app.playlist.playlist.domain

import android.net.Uri
import com.soundhaven.app.playlist.database.db.entity.PlayListEntity
import com.soundhaven.app.playlist.database.db.entity.PlayListTrackEntity
import com.soundhaven.app.playlist.playlist.domain.models.PlayList
import com.soundhaven.app.playlist.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlayListRepository {
    suspend fun insertPlayList(playList: PlayListEntity)
    suspend fun getPlayLists(): Flow<List<PlayList>>
    suspend fun deletePlayList(playList: PlayListEntity)
    suspend fun saveImageToPrivateStorage(uri: Uri?)
    suspend fun getImage(id:String): Uri
    suspend fun insertTrackPlayList(trackEntity: PlayListTrackEntity, playList: PlayListEntity)
    suspend fun deleteTrackFromPlayList(trackEntity: PlayListTrackEntity, playList: PlayListEntity, trackList:MutableList<PlayListTrackEntity>)
    fun getPlayList(idPlayList:Int?):Flow<PlayList>
    fun getTrack(trackId:String?):Flow<List<Track>>
    suspend fun udpadePlayList(playList: PlayList)
    suspend fun deletePlayList(idPlayList: Int)

}