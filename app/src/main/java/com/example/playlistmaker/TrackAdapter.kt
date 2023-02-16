package com.example.playlistmaker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TrackAdapter(): RecyclerView.Adapter<TrackViewHolder>() {
    var track = ArrayList<Track>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {

        return TrackViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_track, parent,
            false))
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(track.get(position))
    }

    override fun getItemCount(): Int {
        return track.size
    }
}