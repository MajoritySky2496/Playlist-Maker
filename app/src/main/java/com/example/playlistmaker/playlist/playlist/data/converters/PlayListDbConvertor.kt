package com.example.playlistmaker.playlist.playlist.data.converters

import com.example.playlistmaker.playlist.playlist.data.db.PlayListEntity
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList

class PlayListDbConvertor {

    fun map(playList: PlayListEntity): PlayList {
        return PlayList(
            playList.playListId,
            playList.name,
            playList.description,
            playList.image,
            playList.idTracks,
            playList.numberTracks
        )
    }
    fun map(playList: PlayList):PlayListEntity{
        return PlayListEntity(playList.playListId,
            playList.name,
            playList.description,
            playList.image,
            playList.idTracks,
            playList.numberTracks)
    }
}