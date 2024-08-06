package com.example.soundhaven.playlist.sharing.data

import com.example.soundhaven.playlist.sharing.domain.model.EmailData

interface ExternalNavigator {
    fun shareLink(text:String)
    fun openEmail(supportEmail:EmailData, subject1:String?, subject2: String?)
    fun openLink(termsLink:String)
}