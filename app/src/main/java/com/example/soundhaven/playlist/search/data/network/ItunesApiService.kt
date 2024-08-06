package com.example.soundhaven.playlist.search.data.network


import com.example.soundhaven.playlist.search.data.dto.TrackSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApiService {

    @GET("/search?entity=song")
    suspend fun search(@Query("term") text:String): TrackSearchResponse
}