package com.example.playlistmaker.playlist.playlist.data.converters

import com.example.playlistmaker.playlist.database.db.entity.TrackEntity
import com.example.playlistmaker.playlist.database.db.entity.PlayListEntity
import com.example.playlistmaker.playlist.database.db.entity.PlayListTrackEntity
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import com.example.playlistmaker.playlist.search.domain.models.Track

class PlayListDbConvertor {

    fun convertToPlayList(playList: PlayListEntity): PlayList {
        return PlayList(
            playList.playListId,
            playList.name,
            playList.description,
            playList.image,
            playList.idTracks,
            playList.numberTracks
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
    fun convertToTrack(track: TrackEntity): Track {
        return Track(
            track.trackId,
            track.artistName,
            track.trackName,
            track.artistName,
            track.primaryGenreName,
            track.country,
            track.collectionName,
            track.artworkUrl100,
            track.trackTimeMillis,
            track.previewUrl,


        )
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
            track.artworkUrl100,
            track.trackTimeMillis,
            track.previewUrl,


            )
    }

}