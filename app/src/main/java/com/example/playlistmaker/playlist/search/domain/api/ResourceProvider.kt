package com.example.playlistmaker.playlist.search.domain.api

interface ResourceProvider {
    fun getString(strRres: Int):String
}