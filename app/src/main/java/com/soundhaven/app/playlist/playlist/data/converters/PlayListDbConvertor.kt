package com.soundhaven.app.playlist.playlist.data.converters

import com.soundhaven.app.playlist.database.db.entity.PlayListEntity
import com.soundhaven.app.playlist.database.db.entity.PlayListTrackEntity
import com.soundhaven.app.playlist.playlist.domain.models.PlayList
import com.soundhaven.app.playlist.search.domain.models.Track

class PlayListDbConvertor {

    fun convertToPlayList(playList: PlayListEntity?): PlayList {
        return PlayList(
            playList?.playListId,
            playList?.name,
            playList?.description,
            playList?.image,
            playList?.idTracks,
            playList?.numberTracks
        )
    }
    fun convertToPlayListEntity(playList: PlayList): PlayListEntity {
        return PlayListEntity(playList.playListId,
            playList.name,
            playList.description,
            playList.image,
            playList.idTracks,
            playList.numberTracks)
    }

    fun convertToTrack(track: PlayListTrackEntity): Track {
        return Track(
            track.trackId,
            track.artistName,
            track.trackName,
            track.artistName,
            track.primaryGenreName,
            track.country,
            track.collectionName,
            track.artworkUrl60,
            track.artworkUrl100,
            track.trackTimeMillis,
            track.previewUrl,
            )
    }

}