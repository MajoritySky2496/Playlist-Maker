package com.example.playlistmaker.playlist.mediateca.data.converters

import com.example.playlistmaker.playlist.mediateca.data.db.TrackDao
import com.example.playlistmaker.playlist.mediateca.data.db.TrackEntity
import com.example.playlistmaker.playlist.search.data.dto.TrackDto
import com.example.playlistmaker.playlist.search.domain.models.Track

class TrackDbConvertor {

    fun map(track:TrackDto):TrackEntity{
        return TrackEntity(track.trackId,
            track.artistName,
            track.trackName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.collectionName,
            track.artworkUrl100,
            track.trackTimeMillis,
            track.previewUrl,)
    }
    fun map(track: TrackEntity):Track{
        return Track(track.trackId,
            track.artistName,
            track.trackName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.collectionName,
            track.artworkUrl100,
            track.trackTimeMillis,
            track.previewUrl,)
    }
    fun map(track: Track):TrackEntity{
        return TrackEntity(track.trackId,
            track.artistName,
            track.trackName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.collectionName,
            track.artworkUrl100,
            track.trackTimeMillis,
            track.previewUrl)
    }
}