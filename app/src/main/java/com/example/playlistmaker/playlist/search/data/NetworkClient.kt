package com.example.playlistmaker.playlist.search.data

import com.example.playlistmaker.playlist.search.data.dto.Response

interface NetworkClient {
    fun doRequest(dto:Any):Response
}