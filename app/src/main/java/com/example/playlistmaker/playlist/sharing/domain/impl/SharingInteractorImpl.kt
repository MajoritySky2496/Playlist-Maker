package com.example.playlistmaker.playlist.sharing.domain.impl


import com.example.playlistmaker.playlist.sharing.data.ExternalNavigator
import com.example.playlistmaker.playlist.sharing.domain.SharingInteractor
import com.example.playlistmaker.playlist.sharing.domain.model.EmailData

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
        const val LINK_YANDEX = "https://practicum.yandex.ru/android-developer"
        const val EMAIL_ADDRESS = "g.zagovenko@rozac.ru"
        const val TERM_LINK = "https://yandex.ru/legal/practicum_offer"
    }

}