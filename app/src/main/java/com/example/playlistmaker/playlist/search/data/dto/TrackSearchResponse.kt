package com.example.playlistmaker.playlist.search.data.dto

import com.example.playlistmaker.playlist.search.domain.models.Track

data class TrackSearchResponse(
    val results: List<Track>
):Response()