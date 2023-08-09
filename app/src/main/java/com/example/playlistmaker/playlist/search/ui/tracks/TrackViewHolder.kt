package com.example.playlistmaker.playlist.search.ui.tracks

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.search.domain.models.Track
import java.text.SimpleDateFormat
import java.util.*

class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var artistName: TextView = itemView.findViewById(R.id.playerNumberOfTracks)
    private var trackName: TextView = itemView.findViewById(R.id.playerPlayLists)
    private var trackTimeMillis: TextView = itemView.findViewById(R.id.timeTack)
    private var artworkUrl: ImageView = itemView.findViewById(R.id.yourImage)

    fun bind(model: Track) {
        artistName.text = model.artistName
        trackName.text = model.trackName
        trackTimeMillis.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(model.trackTimeMillis)

        Glide.with(itemView).load(model.artworkUrl100)
            .placeholder(R.drawable.ic_placeholder).centerInside()
            .apply(RequestOptions.bitmapTransform(RoundedCorners(itemView.resources.getDimensionPixelSize(
                R.dimen.radius_image_track
            ))))
            .into(artworkUrl)

    }
}



