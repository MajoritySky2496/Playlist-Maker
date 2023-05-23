package com.example.playlistmaker.playlist.sharing.data

import com.example.playlistmaker.playlist.sharing.domain.model.EmailData

interface ExternalNavigator {
    fun shareLink(message:String)
    fun openEmail(supportEmail:EmailData)
    fun openLink(termsLink:String)
}