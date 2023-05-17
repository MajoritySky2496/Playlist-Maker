package com.example.playlistmaker.playlist.search.ui.tracks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.search.domain.models.Track

class TrackAdapter(private val onClickListener: TrackClickListener? = null) :
    RecyclerView.Adapter<TrackViewHolder>() {

    var track = mutableListOf<Track>()
    var onItemClick: ((Track) -> Unit)? = null

    fun deleteList(track: List<Track>, adapter: TrackAdapter) {
        track.toMutableList().clear()
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