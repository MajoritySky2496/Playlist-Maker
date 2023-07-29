package com.example.playlistmaker.playlist.playlist.domain.impl

import android.net.Uri
import com.example.playlistmaker.playlist.playlist.data.converters.PlayListDbConvertor
import com.example.playlistmaker.playlist.playlist.domain.PlayListInteractor
import com.example.playlistmaker.playlist.playlist.domain.PlayListRepository
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import kotlinx.coroutines.flow.Flow

class PlayListInteractorImpl(private val playListRepository: PlayListRepository, private val converter:PlayListDbConvertor):PlayListInteractor {
    override suspend fun insertPlayList(playList: PlayList) {
        playListRepository.insertPlayList(converter.map(playList))
    }

    override suspend fun getPlayLists(): Flow<List<PlayList>> {
        return playListRepository.getPlayLists()
    }

    override suspend fun deletePlayList(playList: PlayList) {
        TODO("Not yet implemented")
    }

    override fun saveImageToPrivateStorage(uri: Uri, id: String) {
        playListRepository.saveImageToPrivateStorage(uri, id)

    }

    override fun getImage(id: String): Uri {
        return playListRepository.getImage(id)
    }
}