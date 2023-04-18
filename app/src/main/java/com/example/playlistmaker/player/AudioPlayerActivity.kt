package com.example.playlistmaker.player

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.postDelayed
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.playlistmaker.HISTORY_TRACK_KEY
import com.example.playlistmaker.R
import com.example.playlistmaker.Track
import kotlinx.android.synthetic.main.activity_audioplayer.taimer
import java.text.SimpleDateFormat
import java.util.*

class AudioPlayerActivity : AppCompatActivity()  {

    private var playerState = STATE_DEFAULT
    private var mediaPlayer = MediaPlayer()
    private lateinit var track:Track

    lateinit var handler: Handler
    lateinit var backButton: ImageView
    lateinit var image: ImageView
    lateinit var nameTrack: TextView
    lateinit var nameActor: TextView
    lateinit var time: TextView
    lateinit var country: TextView
    lateinit var collectionName: TextView
    lateinit var releaseDate: TextView
    lateinit var primaryGenreName: TextView
    lateinit var play: ImageView
    lateinit var taimer: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audioplayer)
        handler = Handler(Looper.getMainLooper())
        initViews()
        listeners()


        track = intent.getParcelableExtra<Track>(Track::class.java.simpleName) as Track
            getTrack(track)




        if (track.previewUrl == null) {
            play.isEnabled = false
        } else {
            preparePlayer()
        }




        play.setOnClickListener {
            playbackControl()
        }

    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(timeUpdate)
        mediaPlayer.release()
    }

    override fun onStop() {
        super.onStop()
        pausePlayer()
    }


    private fun initViews() {

        backButton = findViewById(R.id.left_arrow)
        image = findViewById(R.id.trackImage)
        nameTrack = findViewById(R.id.nameTrack)
        nameActor = findViewById(R.id.nameArtist)
        time = findViewById(R.id.time)
        country = findViewById(R.id.countryInf)
        collectionName = findViewById(R.id.album)
        releaseDate = findViewById(R.id.year)
        primaryGenreName = findViewById(R.id.genre)
        play = findViewById(R.id.play)
        taimer = findViewById(R.id.taimer)

    }

    private fun listeners() {
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun getTrack(track: Track) {

        releaseDate.text = track.releaseDate
        nameTrack.text = track.trackName
        nameTrack.text = track.trackName
        nameActor.text = track.artistName
        time.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
        country.text = track.country
        collectionName.text = track.collectionName

        primaryGenreName.text = track.primaryGenreName
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
        if(track.releaseDate!=null){
            releaseDate.text = track.releaseDate
        }else{
            releaseDate.text = " "
        }


    }

    private fun preparePlayer() {
        val url = track.previewUrl
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()



        mediaPlayer.setOnPreparedListener {
            play.isEnabled = true
            playerState = STATE_PREPARED


        }
        mediaPlayer.setOnCompletionListener {
            handler.removeCallbacks(timeUpdate)
            taimer.text = getString(R.string.startTime)
            play.setImageResource(R.drawable.ic_play)
            playerState = STATE_PREPARED
        }

    }

    private fun startPlayer() {
        mediaPlayer.start()
        play.setImageResource(R.drawable.ic_pause)
        playerState = STATE_PLAYING
        handler.postDelayed(timeUpdate, DELAY)
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        play.setImageResource(R.drawable.ic_play)
        playerState = STATE_PAUSED
        handler.removeCallbacks(timeUpdate)
    }

    private fun playbackControl() {
        when (playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }
            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
    }

    private val timeUpdate = object : Runnable {
        override fun run() {
            taimer.text =
                (SimpleDateFormat("mm:ss", Locale.getDefault()).format(mediaPlayer.currentPosition))
            handler.postDelayed(this, DELAY)
        }
    }

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val DELAY = 100L
    }


}