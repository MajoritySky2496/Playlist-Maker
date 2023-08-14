package com.example.playlistmaker.playlist.sharing.data

import com.example.playlistmaker.playlist.sharing.domain.model.EmailData

interface ExternalNavigator {
    fun shareLink(text:String)
    fun openEmail(supportEmail:EmailData, subject1:String?, subject2: String?)
    fun openLink(termsLink:String)
}