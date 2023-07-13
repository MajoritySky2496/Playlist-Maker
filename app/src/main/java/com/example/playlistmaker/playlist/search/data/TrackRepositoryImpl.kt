package com.example.playlistmaker.playlist.search.data


import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.mediateca.data.converters.TrackDbConvertor
import com.example.playlistmaker.playlist.mediateca.data.db.AppDatabase
import com.example.playlistmaker.playlist.mediateca.data.db.TrackEntity
import com.example.playlistmaker.playlist.search.data.dto.TrackDto
import com.example.playlistmaker.playlist.search.data.dto.TrackSearchRequest
import com.example.playlistmaker.playlist.search.data.dto.TrackSearchResponse
import com.example.playlistmaker.playlist.search.data.api.ResourceProvider
import com.example.playlistmaker.playlist.search.domain.TracksRepository
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.example.playlistmaker.playlist.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TrackRepositoryImpl(
    private val networkClient: NetworkClient,
    private val trackStorage: TrackStorage,
    private val resourceProvider: ResourceProvider,
    private val appDatabase: AppDatabase,
    private val trackDbConvertor: TrackDbConvertor
) : TracksRepository {
    override fun searchTrack(expression: String): Flow<Resource<List<Track>>> = flow {
        val response = networkClient.doRequest(TrackSearchRequest(expression))
        when (response.resultCode) {
            ERROR -> {
                emit(Resource.Error(resourceProvider.getString(R.string.check_connection)))
            }

            SUCCESS -> {
                with(response as TrackSearchResponse) {
                    val data = checkIsFavorite(mapTrackListFromDto(results))

                    emit(Resource.Success(data))
                }

            }

            else -> {
                emit(Resource.Error(resourceProvider.getString(R.string.server_error)))
            }
        }

    }

    private suspend fun checkIsFavorite(tracks: List<Track>): List<Track> {
        val tracksIdEntity = appDatabase.trackDao().getTracksId()
        tracksIdEntity.forEach{ trackId ->
            tracks.map { if(it.trackId.equals(trackId)) it.isFavorite = true }
        }
        return tracks
    }

    override fun getTrack(
    ): Flow<Array<Track>> = flow {
        val track = checkIsFavorite(mapTrackListFromDto(trackStorage.doRequest().toList()))
        emit(track.toTypedArray())
    }

    override fun writeSharedPrefsTrack(track: List<Track>) {
        trackStorage.doWrite(mapTrackListToDto(track))
    }

    private fun mapTrackListFromDto(list: List<TrackDto>): List<Track> {
        return list.map {
            Track(
                it.trackId, it.artistName, it.trackName, it.releaseDate, it.primaryGenreName,
                it.country, it.collectionName, it.artworkUrl100, it.trackTimeMillis, it.previewUrl
            )
        }
    }

    private fun mapTrackListToDto(list: List<Track>): List<TrackDto> {
        return list.map {
            TrackDto(
                it.trackId, it.artistName, it.trackName, it.releaseDate, it.primaryGenreName,
                it.country, it.collectionName, it.artworkUrl100, it.trackTimeMillis, it.previewUrl
            )
        }
    }

    private fun mapTrackListFromTrackToEntity(list: List<Track>): List<TrackEntity> {
        return list.map {
            TrackEntity(
                it.trackId, it.artistName, it.trackName, it.releaseDate, it.primaryGenreName,
                it.country, it.collectionName, it.artworkUrl100, it.trackTimeMillis, it.previewUrl
            )
        }
    }

    companion object {
        const val ERROR = -1
        const val SUCCESS = 200
    }

}