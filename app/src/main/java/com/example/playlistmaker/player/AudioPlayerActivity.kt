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

class AudioPlayerActivity: AppCompatActivity() {
    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val DELAY = 100L
    }
    private var playerState = STATE_DEFAULT
    private var mediaPlayer = MediaPlayer()
    private var url:String? = null
    private var handler:Handler?=null

    lateinit var backButton: ImageView
    lateinit var image: ImageView
    lateinit var nameTrack: TextView
    lateinit var nameActor: TextView
    lateinit var time: TextView
    lateinit var country: TextView
    lateinit var collectionName: TextView
    lateinit var releaseDate: TextView
    lateinit var primaryGenreName: TextView
    lateinit var play:ImageView
    lateinit var taimer:TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audioplayer)
        handler = Handler(Looper.getMainLooper())
       initVews()
       listeners()

        val track = intent.getParcelableExtra<Track>(HISTORY_TRACK_KEY)!!
        track?.let { getTrack(it) }
        url = "https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview112/v4/ac/c7/d1/acc7d13f-6634-495f-caf6-491eccb505e8/mzaf_4002676889906514534.plus.aac.p.m4a"
        preparePlayer()
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
        handler?.removeCallbacks(timeUpdate)
        mediaPlayer.release()
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
        play = findViewById(R.id.play)
        taimer = findViewById(R.id.taimer)

    }

    private fun listeners() {
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun getTrack(track: Track) {

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
    private fun preparePlayer() {
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        taimer.text = "00:00"


        mediaPlayer.setOnPreparedListener {
            play.isEnabled = true
            playerState = STATE_PREPARED
            taimer.text = "00:00"

        }
        mediaPlayer.setOnCompletionListener {
            play.setImageResource(R.drawable.ic_play)
            playerState = STATE_PREPARED
            taimer.text = "00:00"


        }

    }
    private fun startPlayer() {
        mediaPlayer.start()
        play.setImageResource(R.drawable.ic_pause)
        playerState = STATE_PLAYING
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        play.setImageResource(R.drawable.ic_play)
        playerState = STATE_PAUSED
    }
    private fun playbackControl() {
        when(playerState) {
            STATE_PLAYING -> {
                handler?.removeCallbacks(timeUpdate)
                pausePlayer()


            }
            STATE_PREPARED, STATE_PAUSED -> {
                handler?.postDelayed(timeUpdate, DELAY)
                startPlayer()

            }
        }
    }
//    fun trackPlaybackTime(){
//        when(playerState){
//            STATE_PLAYING ->
//            STATE_PAUSED ->
//            STATE_PREPARED ->
//
//
//        }
//    }
    private val timeUpdate = object :Runnable{
        override fun run() {
            taimer.text = (SimpleDateFormat("mm:ss", Locale.getDefault()).format(mediaPlayer.currentPosition))
            handler?.postDelayed(this, DELAY)
        }
    }






}