package com.soundhaven.app.playlist.sharing.data

import com.soundhaven.app.playlist.sharing.domain.model.EmailData

interface ExternalNavigator {
    fun shareLink(text:String)
    fun openEmail(supportEmail:EmailData, subject1:String?, subject2: String?)
    fun openLink(termsLink:String)
}