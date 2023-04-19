package com.example.playlistmaker.player.presentation

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.playlistmaker.R
import com.example.playlistmaker.Track
import com.example.playlistmaker.data.TracksRouter
import com.example.playlistmaker.player.AudioPlayerActivity
import java.text.SimpleDateFormat
import java.util.*

class PlayerActivity:AppCompatActivity(), PlayerView {
    private var presenter = PlayerPresenter(this)
    private var router = TracksRouter()
    private var mediaPlayer = MediaPlayer()
    private lateinit var track: Track
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
    private val timeUpdate = object : Runnable {
        override fun run() {
            taimer.text =
                (SimpleDateFormat("mm:ss", Locale.getDefault()).format(mediaPlayer.currentPosition))
            handler.postDelayed(this, DELAY)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audioplayer)
        handler = Handler(Looper.getMainLooper())
        track = router.getTruck(intent)
        initVews()
        presenter.backButton()
        drawTrack(track)

    }
    private fun drawTrack(track: Track){
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
    override fun setTheButtonImagePlay() {
        play.setImageResource(R.drawable.ic_play)
    }

    override fun setTheButtonImagePause() {
        play.setImageResource(R.drawable.ic_pause)
    }

    override fun setTheButtonEnabledFalse() {
        play.isEnabled = false
    }

    override fun setTheButtonEnabledTrue() {
        play.isEnabled = true
    }

    override fun setTimerPlay() {
        handler.postDelayed(timeUpdate, DELAY)
    }

    override fun setTimerPause() {
        handler.removeCallbacks(timeUpdate)
    }

    override fun setTimerStart() {
        taimer.text = getString(R.string.startTime)
    }

    override fun preparePlayer() {
        presenter.preparePlayer(track.previewUrl.toString())
    }
    fun initVews(){
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
    override fun finishActivity() {
        backButton.setOnClickListener{
            finish()
        }
    }
    companion object{
        private const val DELAY = 100L
    }
}