package com.example.soundhaven.playlist.player.domain

import kotlinx.coroutines.flow.Flow

interface PlayerRepository {
    fun getTextTrack(artist: String, title: String): Flow<String>
}