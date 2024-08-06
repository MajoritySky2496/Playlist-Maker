package com.example.soundhaven.playlist.search.data

import android.content.Context
import com.example.soundhaven.playlist.search.data.api.ResourceProvider

class ResourceProviderImpl(private val context: Context): ResourceProvider {
    override fun getString(strRres: Int): String {
        return context.getString(strRres)
    }
}