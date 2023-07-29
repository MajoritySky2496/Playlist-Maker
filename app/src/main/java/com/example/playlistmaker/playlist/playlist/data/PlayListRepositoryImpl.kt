package com.example.playlistmaker.playlist.playlist.data

import android.net.Uri
import com.example.playlistmaker.playlist.mediateca.data.db.AppDatabase
import com.example.playlistmaker.playlist.playlist.data.converters.PlayListDbConvertor
import com.example.playlistmaker.playlist.playlist.data.db.AppDatabasePlayList
import com.example.playlistmaker.playlist.playlist.data.db.PlayListEntity
import com.example.playlistmaker.playlist.playlist.domain.PlayListRepository
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlayListRepositoryImpl(private val appDatabase: AppDatabasePlayList,
                             private val converter:PlayListDbConvertor,
                             private val privateStorage: PrivateStorage
):PlayListRepository {
    override suspend fun insertPlayList(playList: PlayListEntity ) {
        appDatabase.playListDao().insertPlayList(playList)

    }

    override suspend fun getPlayLists(): Flow<List<PlayList>> {
       return convertToPlayList(appDatabase.playListDao().getPlayLists())

    }

    override suspend fun deletePlayList(playList: PlayListEntity) {
        TODO("Not yet implemented")
    }

    override fun saveImageToPrivateStorage(uri: Uri, id: String) {
        privateStorage.saveImageToPrivateStorage(uri,id)
    }

    override fun getImage(id: String): Uri {
        return privateStorage.getImage(id)
    }

    private fun convertToPlayList(playListEntity:Flow<List<PlayListEntity>>):Flow<List<PlayList>>{
        return playListEntity.map { listOfPlayList -> listOfPlayList.map { playList->  converter.map(playList) }  }

    }
}