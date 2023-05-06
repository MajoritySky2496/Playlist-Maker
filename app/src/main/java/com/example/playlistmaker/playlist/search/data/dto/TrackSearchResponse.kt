package com.example.playlistmaker.playlist.search.data.dto

import com.example.playlistmaker.playlist.search.domain.models.Track

class TrackSearchResponse(
    val results: List<Track>
):Response() {
}