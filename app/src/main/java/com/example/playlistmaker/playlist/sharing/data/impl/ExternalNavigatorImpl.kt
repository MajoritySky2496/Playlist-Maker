package com.example.playlistmaker.playlist.sharing.data.impl

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import com.example.playlistmaker.R

import com.example.playlistmaker.playlist.sharing.data.ExternalNavigator
import com.example.playlistmaker.playlist.sharing.domain.model.EmailData

class ExternalNavigatorImpl(private val context: Context):ExternalNavigator {
    private val subject1 = context.getText(R.string.subject_1)
    private val subject2 = context.getText(R.string.subject_2)
    override fun shareLink(message: String) {
        val sendIntent: Intent = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, message)
            type = "text/plain"
        }, null)

        context.startActivity(sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    override fun openEmail(supportEmail: EmailData) {
        val shareIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse(supportEmail.mailTo) // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, supportEmail.mail)
            putExtra(Intent.EXTRA_SUBJECT, subject1)
            putExtra(Intent.EXTRA_TEXT, subject2)
        }
        context.startActivity(shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    override fun openLink(termsLink: String) {
        val webpage: Uri = Uri.parse(termsLink)
        val openWeb = Intent(Intent.ACTION_VIEW, webpage)
        context.startActivity(openWeb)
    }
}