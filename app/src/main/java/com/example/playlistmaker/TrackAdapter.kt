package com.example.playlistmaker

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TrackAdapter(private val onClickListener: TrackClickListener? = null) :
    RecyclerView.Adapter<TrackViewHolder>() {

    var track = ArrayList<Track>()
    var onItemClick: ((Track) -> Unit)? = null

    fun deleteList(track: ArrayList<Track>, adapter: TrackAdapter) {
        track.clear()
        adapter.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.activity_track, parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(track.get(position))
        holder.itemView.setOnClickListener {
            onClickListener?.onTrackClick(track.get(position))
            onItemClick?.invoke(track.get(position))
        }
    }

    override fun getItemCount(): Int {
        return track.size
    }

    fun interface TrackClickListener {
        fun onTrackClick(track: Track)

    }

}