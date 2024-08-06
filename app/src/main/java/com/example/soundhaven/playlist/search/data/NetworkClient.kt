package com.example.soundhaven.playlist.search.data

import com.example.soundhaven.playlist.search.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto:Any):Response
    suspend fun getTrackText(artist: String, title: String): Response
}