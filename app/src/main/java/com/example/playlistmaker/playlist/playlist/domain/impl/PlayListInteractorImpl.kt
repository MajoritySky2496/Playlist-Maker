package com.example.playlistmaker.playlist.playlist.domain.impl

import android.net.Uri
import com.example.playlistmaker.playlist.mediateca.data.converters.TrackDbConvertor
import com.example.playlistmaker.playlist.playlist.data.converters.PlayListDbConvertor
import com.example.playlistmaker.playlist.playlist.data.db.PlayListEntity
import com.example.playlistmaker.playlist.playlist.domain.PlayListInteractor
import com.example.playlistmaker.playlist.playlist.domain.PlayListRepository
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import com.example.playlistmaker.playlist.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

class PlayListInteractorImpl(private val playListRepository: PlayListRepository,
                             private val converterPlayList:PlayListDbConvertor,
                             private val converterTrack:TrackDbConvertor
):PlayListInteractor {
    override suspend fun insertPlayList(playList: PlayList) {
        playListRepository.insertPlayList(converterPlayList.map(playList))
    }

    override suspend fun getPlayLists(): Flow<List<PlayList>> {
        return playListRepository.getPlayLists()
    }

    override suspend fun deletePlayList(playList: PlayList) {
        TODO("Not yet implemented")
    }

    override suspend fun saveImageToPrivateStorage(uri: Uri?, id: String) {

        playListRepository.saveImageToPrivateStorage(uri, id)

    }

    override suspend fun getImage(id: String): Uri {
        return playListRepository.getImage(id)
    }

    override suspend fun insertTrackPlayList(track: Track, playList: PlayList) {
        playListRepository.insertTrackPlayList(converterTrack.map(track), converterPlayList.map(playList) )
    }
}