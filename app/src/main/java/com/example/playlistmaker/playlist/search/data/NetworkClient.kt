package com.example.playlistmaker.playlist.search.data

import com.example.playlistmaker.playlist.search.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto:Any):Response
}