package com.example.playlistmaker.playlist.mediateca.data.converters

import com.example.playlistmaker.playlist.database.db.entity.PlayListTrackEntity
import com.example.playlistmaker.playlist.database.db.entity.TrackEntity
import com.example.playlistmaker.playlist.search.domain.models.Track

class TrackDbConvertor {

    fun map(track: TrackEntity):Track{
        return Track(track.trackId,
            track.artistName,
            track.trackName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.collectionName,
            track.artworkUrl100,
            track.artworkUrl60,
            track.trackTimeMillis,
            track.previewUrl,)
    }
    fun map(track: Track): TrackEntity {
        return TrackEntity(track.trackId,
            track.artistName,
            track.trackName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.collectionName,
            track.artworkUrl100,
            track.artworkUrl60,
            track.trackTimeMillis,
            track.previewUrl)
    }
    fun convertToPlayListTrackEntity(track: Track):PlayListTrackEntity{
        return PlayListTrackEntity(track.trackId,
            track.artistName,
            track.trackName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.collectionName,
            track.artworkUrl100,
            track.artworkUrl60,
            track.trackTimeMillis,
            track.previewUrl)
    }
}