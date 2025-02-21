package com.soundhaven.app.playlist.sharing.domain.impl


import com.soundhaven.app.playlist.sharing.data.ExternalNavigator
import com.soundhaven.app.playlist.sharing.domain.SharingInteractor
import com.soundhaven.app.playlist.sharing.domain.model.EmailData

class SharingInteractorImpl(private val externalNavigator: ExternalNavigator) : SharingInteractor {

    override fun shareApp(text:String) {
        externalNavigator.shareLink(text)
    }

    override fun openTerms() {
        externalNavigator.openLink(getTermLink(), )
    }

    override fun openSupport( subject1:String?, subject2: String?) {
        externalNavigator.openEmail(getSupportEmailData(), subject1, subject2)
    }

    private fun getSupportEmailData(): EmailData {
        return EmailData(mail = EMAIL_ADDRESS)

    }

    private fun getTermLink():String{
        return TERM_LINK

    }
    companion object {
        const val LINK_YANDEX = "https://play.google.com/store/apps/details?id=com.soundhaven.app"
        const val EMAIL_ADDRESS = "dashagixy@gmail.com"
        const val TERM_LINK = ""
    }

}