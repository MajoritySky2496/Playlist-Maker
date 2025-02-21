package com.soundhaven.app.playlist.search.data

import com.soundhaven.app.playlist.search.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto:Any):Response
    suspend fun getTrackText(artist: String, title: String): Response
}