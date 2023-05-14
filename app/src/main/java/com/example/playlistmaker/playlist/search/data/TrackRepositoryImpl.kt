package com.example.playlistmaker.playlist.search.data


import android.content.SharedPreferences
import com.example.playlistmaker.TrackResponce
import com.example.playlistmaker.playlist.search.data.dto.TrackDto
import com.example.playlistmaker.playlist.search.data.dto.TrackSearchRequest
import com.example.playlistmaker.playlist.search.data.dto.TrackSearchResponse
import com.example.playlistmaker.playlist.search.data.dto.TrackWriteResponce
import com.example.playlistmaker.playlist.search.data.localwork.SharedPrefsStorage
import com.example.playlistmaker.playlist.search.domain.api.TracksRepository
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.example.playlistmaker.playlist.util.Resource
import kotlin.collections.ArrayList
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map

class TrackRepositoryImpl(private val networkClient: NetworkClient, private val trackStorage: TrackStorage):TracksRepository {
    override fun searchTrack(expression: String): Resource<List<Track>> {
        val response = networkClient.doRequest(TrackSearchRequest(expression))
        return  when(response.resultCode){
            -1 -> {
                Resource.Error("Проверьте подключение к интеренету")
            }
            200 -> {
                Resource.Success((response as TrackSearchResponse).results.map {
                    Track(it.trackId, it.artistName, it.trackName, it.releaseDate, it.primaryGenreName,
                        it.country, it.collectionName, it.artworkUrl100, it.trackTimeMillis, it.previewUrl)
                })
            }
            else -> {
                Resource.Error("Ошибка сервера")
            }
        }

    }

    override fun getTrack(
    ): Array<Track> {
       val track =  trackStorage.doRequest()
        return mapTrackListFromDto(track.toList()).toTypedArray()
    }

    override fun writeSharedPrefsTrack(track: List<Track>) {
        trackStorage.doWrite(mapTrackListToDto(track))
    }

    private fun mapTrackListFromDto(list: List<TrackDto>):List<Track>{
        return list.map {
            Track(it.trackId, it.artistName, it.trackName, it.releaseDate, it.primaryGenreName,
                it.country, it.collectionName, it.artworkUrl100, it.trackTimeMillis, it.previewUrl)
        }
    }
    private fun mapTrackListToDto(list: List<Track>):List<TrackDto>{
        return list.map { TrackDto(it.trackId, it.artistName, it.trackName, it.releaseDate, it.primaryGenreName,
            it.country, it.collectionName, it.artworkUrl100, it.trackTimeMillis, it.previewUrl) }
    }

}