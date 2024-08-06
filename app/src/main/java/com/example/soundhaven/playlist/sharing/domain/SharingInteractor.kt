package com.example.soundhaven.playlist.sharing.domain

interface SharingInteractor {
    fun shareApp(text:String)
    fun openTerms()
    fun openSupport( subject1:String?, subject2: String?)
}