package com.example.playlistmaker.playlist.search.data

import android.content.Context
import com.example.playlistmaker.playlist.search.domain.api.ResourceProvider

class ResourceProviderImpl(private val context: Context): ResourceProvider {
    override fun getString(strRres: Int): String {
        return context.getString(strRres)
    }
}