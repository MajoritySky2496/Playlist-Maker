package com.example.playlistmaker.playlist.playlist.domain

import android.net.Uri
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import com.example.playlistmaker.playlist.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlayListInteractor {
    suspend fun insertPlayList(playList: PlayList)
    suspend fun getPlayLists(): Flow<List<PlayList>>
    suspend fun deletePlayList(playList: PlayList)
    suspend fun saveImageToPrivateStorage(uri: Uri?)
    suspend fun getImage(id:String): Uri
    suspend fun insertTrackPlayList(track: Track, playList: PlayList)
     fun getPlayList(idPlayList:Int?):Flow<PlayList>
     fun getTrackList(idTrack:String?):Flow<List<Track>>

}