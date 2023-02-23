package com.example.playlistmaker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TrackAdapter(): RecyclerView.Adapter<TrackViewHolder>() {
    var track = ArrayList<Track>()
    fun deleteList(track: ArrayList<Track>, adapter: TrackAdapter ){
        track.clear()
        adapter.notifyDataSetChanged()

    }
    var onItemClick: ((Track) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {

        return TrackViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_track, parent,
            false))

    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(track.get(position))
        holder.itemView.setOnClickListener{ onItemClick?.invoke(track.get(position))

        }
    }

    override fun getItemCount(): Int {
        return track.size


    }


}