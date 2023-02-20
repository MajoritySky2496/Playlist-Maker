package com.example.playlistmaker

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {

    var myTheme = R.style.Theme_PlaylistMaker
    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            myTheme = savedInstanceState.getInt("theme")
        }
        setTheme(myTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val shareButton = findViewById<TextView>(R.id.share_button)
        val writeToSupportButton = findViewById<TextView>(R.id.writeToSupport_button)
        val userAgreement_button = findViewById<TextView>(R.id.userAgreement_button)
        val backButton = findViewById<ImageView>(R.id.back_button)
        val themeSwitcher = findViewById<SwitchMaterial>(R.id.themeSwitcher)


        themeSwitcher.setOnCheckedChangeListener{ switcher, cheched ->
            (applicationContext as App).switchTheme(cheched)

        }


        shareButton.setOnClickListener {
            val message = getString(R.string.link_to_yandexPracticum)
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, message)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
        writeToSupportButton.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:") // only email apps should handle this
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.email_address)))
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject_1))
                putExtra(Intent.EXTRA_TEXT, getString(R.string.subject_2))
            }
            startActivity(shareIntent)
        }
        userAgreement_button.setOnClickListener {
            val webpage: Uri = Uri.parse(getString(R.string.user_agriment))
            val openWeb = Intent(Intent.ACTION_VIEW, webpage)
            startActivity(openWeb)
        }
        backButton.setOnClickListener {
            finish()
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("theme", myTheme)
    }


}
