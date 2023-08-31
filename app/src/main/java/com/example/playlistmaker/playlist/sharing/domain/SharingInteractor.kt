package com.example.playlistmaker.playlist.sharing.domain

import com.example.playlistmaker.playlist.sharing.domain.model.EmailData

interface SharingInteractor {
    fun shareApp(text:String)
    fun openTerms()
    fun openSupport( subject1:String?, subject2: String?)
}