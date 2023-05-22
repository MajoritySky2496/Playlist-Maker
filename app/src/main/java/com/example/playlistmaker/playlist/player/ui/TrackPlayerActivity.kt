package com.example.playlistmaker.playlist.player.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.player.presentation.PlayerViewModel
import com.example.playlistmaker.playlist.player.ui.models.PlayStatus
import com.example.playlistmaker.playlist.player.ui.models.Taimer
import com.example.playlistmaker.playlist.player.ui.models.TrackScreenState
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.example.playlistmaker.playlist.util.NavigationRouter
import java.text.SimpleDateFormat
import java.util.*

class TrackPlayerActivity:ComponentActivity() {



    private lateinit var track: Track
    lateinit var trackUrl:String
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
    lateinit var progressBar: ProgressBar
    lateinit var playerScreen: View

    private val viewModel by lazy{
        ViewModelProvider(this, PlayerViewModel.getViewModelFactory(track)
        )[PlayerViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audioplayer)
        initVews()
        track = NavigationRouter().getTruck(intent)
        trackUrl = track.previewUrl.toString()
        viewModel.getScreenStateLiveData().observe(this){render(it)}
        viewModel.getPlayStatusLiveData().observe(this){changePlayStatus(it)}
        viewModel.getTaimerStatusLiveData().observe(this){taimer(it)}


        play.setOnClickListener{
            viewModel.playBackControl()

        }
        backButton.setOnClickListener{
            NavigationRouter().goBack(this)

        }

    }

    private fun timeUpdate(currentPosition:Int){

        taimer.text =
            (SimpleDateFormat("mm:ss", Locale.getDefault()).format(currentPosition))
    }
    private fun drawTrack(track: Track) {
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

    }
    fun initVews() {
        playerScreen = findViewById(R.id.playerScreen)
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
        taimer = findViewById(R.id.timer)
        progressBar = findViewById(R.id.progressBar)
    }
    fun render(state: TrackScreenState){
        when(state){
            is TrackScreenState.Content -> {
                changeContentVisibility(loading = false)
                drawTrack(state.track)
            }
            is TrackScreenState.Loading ->{
                changeContentVisibility(loading = true)
            }


        }

    }
    fun taimer(time:Taimer){
        when(time){
            is Taimer.SetTimeReset -> {
                setTimerReset(time.timeReset)
            }
            is Taimer.TimeUpdate -> {
                timeUpdate(time.currentPosition)
            }
        }
    }
    fun changePlayStatus(playStatus: PlayStatus){
        changeButtonStyle(playStatus)
    }


    private fun changeContentVisibility(loading:Boolean) {
        progressBar.isVisible = loading
        playerScreen.isVisible = !loading

    }
    private fun setTimerReset(timeReset:String) {
        taimer.text = timeReset
    }
    private fun changeButtonStyle(playStatus: PlayStatus){
        if (playStatus.isPlaying == false){
            play.setImageResource(R.drawable.ic_play)
        }else{
            play.setImageResource(R.drawable.ic_pause)


        }


    }
}