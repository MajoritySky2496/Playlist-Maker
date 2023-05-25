package com.example.playlistmaker.playlist.search.data


import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.search.data.dto.TrackDto
import com.example.playlistmaker.playlist.search.data.dto.TrackSearchRequest
import com.example.playlistmaker.playlist.search.data.dto.TrackSearchResponse
import com.example.playlistmaker.playlist.search.domain.api.ResourceProvider
import com.example.playlistmaker.playlist.search.domain.api.TracksRepository
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.example.playlistmaker.playlist.util.Resource

class TrackRepositoryImpl(private val networkClient: NetworkClient,
                          private val trackStorage: TrackStorage,
                          private val resourceProvider: ResourceProvider
):TracksRepository {
    override fun searchTrack(expression: String): Resource<List<Track>> {
        val response = networkClient.doRequest(TrackSearchRequest(expression))
        return  when(response.resultCode){
            ERROR -> {
                Resource.Error(resourceProvider.getString(R.string.check_connection))
            }
            SUCCESS -> {
                Resource.Success((response as TrackSearchResponse).results.map {
                    Track(it.trackId, it.artistName, it.trackName, it.releaseDate, it.primaryGenreName,
                        it.country, it.collectionName, it.artworkUrl100, it.trackTimeMillis, it.previewUrl)
                })
            }
            else -> {
                Resource.Error(resourceProvider.getString(R.string.server_error))
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
    companion object{
        const val ERROR = -1
        const val SUCCESS = 200
    }

}