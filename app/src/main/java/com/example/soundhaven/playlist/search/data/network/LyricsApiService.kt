package com.example.soundhaven.playlist.search.data.network

import com.example.soundhaven.playlist.player.data.dto.TrackTextResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface LyricsApiService {

    @GET("v1/{artist}/{title}")
    suspend fun getTrackText(
        @Path("artist") artist: String,
        @Path("title") title: String
    ): TrackTextResponse
}