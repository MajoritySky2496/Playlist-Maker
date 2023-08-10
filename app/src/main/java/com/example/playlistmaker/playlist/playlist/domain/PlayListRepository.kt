package com.example.playlistmaker.playlist.playlist.domain

import android.net.Uri
import com.example.playlistmaker.playlist.mediateca.data.db.TrackEntity
import com.example.playlistmaker.playlist.playlist.data.db.PlayListEntity
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import com.example.playlistmaker.playlist.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlayListRepository {
    suspend fun insertPlayList(playList: PlayListEntity)
    suspend fun getPlayLists(): Flow<List<PlayList>>
    suspend fun deletePlayList(playList: PlayListEntity)
    suspend fun saveImageToPrivateStorage(uri: Uri?)
    suspend fun getImage(id:String): Uri
    suspend fun insertTrackPlayList(trackEntity: TrackEntity, playList: PlayListEntity)
     fun getPlayList(idPlayList:Int?):Flow<PlayList>
    fun getTrack(trackId:String?):Flow<List<Track>>

}