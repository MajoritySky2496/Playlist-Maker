package com.soundhaven.app.playlist.search.data.network

import com.soundhaven.app.playlist.player.data.dto.TrackTextResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface LyricsApiService {

    @GET("v1/{artist}/{title}")
    suspend fun getTrackText(
        @Path("artist") artist: String,
        @Path("title") title: String
    ): TrackTextResponse
}