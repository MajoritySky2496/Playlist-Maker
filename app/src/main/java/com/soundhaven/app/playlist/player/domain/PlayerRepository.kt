package com.soundhaven.app.playlist.player.domain

import kotlinx.coroutines.flow.Flow

interface PlayerRepository {
    fun getTextTrack(artist: String, title: String): Flow<String>
}