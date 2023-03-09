package com.example.playlistmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlin.collections.ArrayList
import java.text.SimpleDateFormat
import java.util.*

class MediatecaActivity : AppCompatActivity() {
    lateinit var backButton: ImageView
    lateinit var image: ImageView
    lateinit var nameTrack: TextView
    lateinit var nameActor: TextView
    lateinit var time: TextView
    lateinit var country: TextView
    lateinit var collectionName: TextView
    lateinit var releaseDate: TextView
    lateinit var primaryGenreName: TextView
    var trackList = ArrayList<Track>()
    lateinit var track: Track


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mediateca)
        initVews()
        listeners()
        val sharedPrefrs = getSharedPreferences(PRACTICUM_EXAMPLE_PREFERENCES, MODE_PRIVATE)
        val searchHistory = SearchHistory(sharedPrefrs)
        trackList.addAll(searchHistory.getHistory())
        track = trackList.get(0)
        getTrack()


    }

    fun initVews() {
        backButton = findViewById(R.id.left_arrow)
        image = findViewById(R.id.trackImage)
        nameTrack = findViewById(R.id.nameTrack)
        nameActor = findViewById(R.id.nameArtist)
        time = findViewById(R.id.time)
        country = findViewById(R.id.countryInf)
        collectionName = findViewById(R.id.album)
        releaseDate = findViewById(R.id.year)
        primaryGenreName = findViewById(R.id.genre)

    }

    private fun listeners() {
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun getTrack() {
        nameTrack.setText(track.trackName)
        nameActor.setText(track.artistName)
        time.setText(SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis))
        country.setText(track.country)
        collectionName.setText(track.collectionName)
        releaseDate.setText(track.releaseDate)
        primaryGenreName.setText(track.primaryGenreName)
        Glide.with(image).load(
            track.artworkUrl100
                .replaceAfterLast('/', "512x512bb.jpg")
        )
            .placeholder(R.drawable.ic_placeholder_mediateca)
            .apply(
                (RequestOptions.bitmapTransform(
                    RoundedCorners(
                        image.resources.getDimensionPixelSize(
                            R.dimen.radius_image_track
                        )
                    )
                ))
            ).into(image)


    }


}