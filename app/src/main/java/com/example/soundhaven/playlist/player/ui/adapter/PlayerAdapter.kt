package com.example.soundhaven.playlist.player.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.soundhaven.R
import com.example.soundhaven.playlist.player.ui.viewholder.PlayerViewHolder
import com.example.soundhaven.playlist.playlist.domain.models.PlayList

class PlayerAdapter(private val onClickListener: TrackClickListener? = null) :
RecyclerView.Adapter<PlayerViewHolder>()  {

    var playList = mutableListOf<PlayList>()
    var onItemClick: ((PlayList) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder( LayoutInflater.from(parent.context).inflate(
            R.layout.element_playlist_audioplayer, parent,
            false
        ))
    }

    override fun getItemCount(): Int {
        return playList.size
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(playList.get(position))
        holder.itemView.setOnClickListener {
            onClickListener?.onTrackClick(playList.get(position))
            onItemClick?.invoke(playList.get(position))
        }
    }
    fun interface TrackClickListener {
        fun onTrackClick(playList: PlayList)

    }
}