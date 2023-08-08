package com.example.playlistmaker.playlist.mediateca.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.mediateca.ui.viewholder.PlayListsViewHolder
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import com.example.playlistmaker.playlist.playlist.presentation.PlayListViewModel
import com.example.playlistmaker.playlist.search.domain.models.Track

class PlayListAdapter:RecyclerView.Adapter<PlayListsViewHolder>() {
    var playList = mutableListOf<PlayList>()
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

    }


}