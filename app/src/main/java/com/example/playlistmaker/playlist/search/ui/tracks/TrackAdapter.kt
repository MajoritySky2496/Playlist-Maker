package com.example.playlistmaker.playlist.search.ui.tracks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.search.domain.models.Track

class TrackAdapter(private val onClickListener: TrackClickListener? = null, private val onLongClickListener:TrackLongClickListener? = null, ) :
    RecyclerView.Adapter<TrackViewHolder>() {

    var track = mutableListOf<Track>()
    var highQuality = true
    var onItemClick: ((Track) -> Unit)? = null
     var onItemLongClick: ((Track?) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.activity_track, parent,
                false
            ),highQuality
        )
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(track.get(position))
        holder.itemView.setOnClickListener {
            onClickListener?.onTrackClick(track.get(position))
            onItemClick?.invoke(track.get(position))

        }
        holder.itemView.setOnLongClickListener{
            onLongClickListener?.onLongTrackClick(track.get(position))
            onItemLongClick?.invoke(track.get(position))
            true
        }

    }

    override fun getItemCount(): Int {
        return track.size
    }

    fun interface TrackClickListener {
        fun onTrackClick(track: Track)


    }
     fun interface TrackLongClickListener{
        fun onLongTrackClick(track: Track)
    }

}