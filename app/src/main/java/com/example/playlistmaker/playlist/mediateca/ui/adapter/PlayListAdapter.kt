package com.example.playlistmaker.playlist.mediateca.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.mediateca.ui.viewholder.PlayListsViewHolder
import com.example.playlistmaker.playlist.player.ui.adapter.PlayerAdapter
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList

class PlayListAdapter(private val onClickListener: PlayerAdapter.TrackClickListener? = null):RecyclerView.Adapter<PlayListsViewHolder>() {
    var playList = mutableListOf<PlayList>()
    var onItemClick: ((PlayList) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListsViewHolder {
        return PlayListsViewHolder( LayoutInflater.from(parent.context).inflate(
            R.layout.element_playlist, parent,
            false
        ))
    }

    override fun getItemCount(): Int {
        return playList.size
    }

    override fun onBindViewHolder(holder: PlayListsViewHolder, position: Int) {
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