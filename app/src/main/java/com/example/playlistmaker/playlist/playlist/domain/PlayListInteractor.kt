package com.example.playlistmaker.playlist.playlist.domain

import android.net.Uri
import com.example.playlistmaker.playlist.playlist.data.db.PlayListEntity
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import kotlinx.coroutines.flow.Flow

interface PlayListInteractor {
    suspend fun insertPlayList(playList: PlayList)
    suspend fun getPlayLists(): Flow<List<PlayList>>
    suspend fun deletePlayList(playList: PlayList)
    suspend fun saveImageToPrivateStorage(uri: Uri?, id:String)
    suspend fun getImage(id:String): Uri

}