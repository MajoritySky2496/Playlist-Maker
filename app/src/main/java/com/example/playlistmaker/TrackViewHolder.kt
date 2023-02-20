package com.example.playlistmaker

import android.os.Build
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.NonDisposableHandle.parent

class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var artistName: TextView = itemView.findViewById(R.id.artistName)
    private var trackName: TextView = itemView.findViewById(R.id.nameTrack)
    private var trackTime: TextView = itemView.findViewById(R.id.timeTack)
    private var artworkUrl: ImageView = itemView.findViewById(R.id.image_url)


    fun bind(model: Track) {
        artistName.text = model.artistName
        trackName.text = model.trackName
        trackTime.text = model.trackTime

        Glide.with(itemView).load(model.artworkUrl100)
            .placeholder(R.drawable.ic_placeholder).centerInside()
            .apply(RequestOptions.bitmapTransform(RoundedCorners(12)))
            .into(artworkUrl)


    }

}


