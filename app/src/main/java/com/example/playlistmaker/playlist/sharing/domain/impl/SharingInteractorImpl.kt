package com.example.playlistmaker.playlist.sharing.domain.impl

import com.example.playlistmaker.playlist.sharing.data.ExternalNavigator
import com.example.playlistmaker.playlist.sharing.domain.SharingInteractor
import com.example.playlistmaker.playlist.sharing.domain.model.EmailData

class SharingInteractorImpl(private val externalNavigator: ExternalNavigator): SharingInteractor {
    override fun shareApp() {
//        externalNavigator.shareLink(getShareAppLink())
    }

    override fun openTerms() {
//        externalNavigator.openLink(getTermLink())
    }

    override fun openSupport() {
//        externalNavigator.openEmail(getSupportEmailData())
    }
//    private fun getShareAppLink():String{
//
//    }
//
//    private fun getSupportEmailData(): EmailData {
//
//    }
//
//    private fun getTermLink():String{
//
//    }

}