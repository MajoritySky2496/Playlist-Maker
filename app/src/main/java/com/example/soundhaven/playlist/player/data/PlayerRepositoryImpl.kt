package com.example.soundhaven.playlist.player.data

import com.example.soundhaven.R
import com.example.soundhaven.playlist.player.data.dto.TrackTextResponse
import com.example.soundhaven.playlist.player.domain.PlayerRepository
import com.example.soundhaven.playlist.search.data.NetworkClient
import com.example.soundhaven.playlist.search.data.TrackRepositoryImpl
import com.example.soundhaven.playlist.search.data.api.ResourceProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlayerRepositoryImpl(
    private val networkClient: NetworkClient,
    private val resourceProvider: ResourceProvider
) : PlayerRepository {

    override fun getTextTrack(artist: String, title: String): Flow<String> = flow {
        val response = networkClient.getTrackText(artist, title)
        when (response.resultCode) {
            TrackRepositoryImpl.ERROR -> {
                emit(resourceProvider.getString(R.string.check_connection))
            }
            TrackRepositoryImpl.SUCCESS -> {
                with(response as TrackTextResponse) { emit(lyrics) }
            }
            else -> {
                emit(resourceProvider.getString(R.string.server_error))
            }
        }
    }
}