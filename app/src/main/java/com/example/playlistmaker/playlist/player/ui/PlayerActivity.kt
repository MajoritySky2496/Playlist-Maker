package com.example.playlistmaker.playlist.player.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.player.presentation.PlayerViewModel
import com.example.playlistmaker.playlist.player.ui.models.PlayStatus
import com.example.playlistmaker.playlist.player.ui.models.Timer
import com.example.playlistmaker.playlist.player.ui.models.TrackScreenState
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.example.playlistmaker.playlist.util.NavigationRouter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.*


class PlayerActivity : AppCompatActivity() {

    private lateinit var track: Track
    lateinit var trackUrl: String
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
    lateinit var like:ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audioplayer)
        initVews()
        track = intent.getParcelableExtra<Track>(Track::class.java.simpleName) as Track
        val viewModel: PlayerViewModel by  viewModel{
            parametersOf(track)
        }

        trackUrl = track.previewUrl.toString()
        viewModel.getScreenStateLiveData().observe(this) { render(it) }
        viewModel.getPlayStatusLiveData().observe(this) { changePlayStatus(it) }
        viewModel.getTaimerStatusLiveData().observe(this) { taimer(it) }
        viewModel.checkIsFavoriteCliked()
        viewModel.drawTrack()





        play.setOnClickListener {
            viewModel.playBackControl()

        }
        backButton.setOnClickListener {
            NavigationRouter().goBack(this)
        }
        like.setOnClickListener {
            setResult(RESULT_OK)
            viewModel.onFavoriteClicked()
        }
    }

    private fun timeUpdate(currentPosition: Int) {

        taimer.text =
            (SimpleDateFormat("mm:ss", Locale.getDefault()).format(currentPosition))
    }

    private fun drawTrack(track: Track, isFavorite: Boolean) {
        changeButtonLike(isFavorite)
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
        like = findViewById(R.id.like)
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
        like = findViewById(R.id.like)
    }

    fun render(state: TrackScreenState) {
        when (state) {
            is TrackScreenState.Content -> {
                changeContentVisibility(loading = false)
            }
            is TrackScreenState.Loading -> {
                changeContentVisibility(loading = true)
            }
            is TrackScreenState.DrawTrack -> {
                drawTrack(state.track, state.isFavotite)
            }




        }

    }
    private fun changeButtonLike(isFavorite:Boolean){
        if(isFavorite!=false){
            like.setImageResource(R.drawable.like_iscliked)
        }else{
            like.setImageResource(R.drawable.ic_like)
        }

    }

    fun taimer(time: Timer) {
        when (time) {
            is Timer.SetTimeReset -> {
                setTimerReset(time.timeReset)
            }
            is Timer.TimeUpdate -> {
                timeUpdate(time.currentPosition)
            }
        }
    }

    fun changePlayStatus(playStatus: PlayStatus) {
        changeButtonStyle(playStatus)
    }

    private fun changeContentVisibility(loading: Boolean) {
        progressBar.isVisible = loading
        playerScreen.isVisible = !loading

    }

    private fun setTimerReset(timeReset: String) {
        taimer.text = timeReset
    }

    private fun changeButtonStyle(playStatus: PlayStatus) {
        if (playStatus.isPlaying) {
            play.setImageResource(R.drawable.ic_pause)
        } else {
            play.setImageResource(R.drawable.ic_play)
        }


    }
}