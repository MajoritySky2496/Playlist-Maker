package com.example.playlistmaker.playlist.player.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.mediateca.ui.viewholder.PlayListsViewHolder
import com.example.playlistmaker.playlist.player.ui.viewholder.PlayerViewHolder
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.example.playlistmaker.playlist.search.ui.tracks.TrackAdapter

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