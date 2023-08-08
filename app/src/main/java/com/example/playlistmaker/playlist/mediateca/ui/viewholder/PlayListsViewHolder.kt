package com.example.playlistmaker.playlist.mediateca.ui.viewholder

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import com.example.playlistmaker.playlist.util.Resource

class PlayListsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var image: ImageView = itemView.findViewById(R.id.yourImage)
    private var namePlatList: TextView = itemView.findViewById(R.id.nameOfPlayList)
    private var numberOfTrack: TextView = itemView.findViewById(R.id.numberOfTracks)


    fun bind(model: PlayList) {
        val transformation = MultiTransformation(CenterCrop(), RoundedCorners(10))



        Glide.with(itemView).load(model.image)
            .placeholder(R.drawable.ic_placeholder).centerCrop()
            .transform(transformation)
            .into(image)
        namePlatList.text = model.name

        numberOfTrack.text = model.numberTracks


    }


}