package com.example.playlistmaker.playlist.player.ui.viewholder

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

class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var image: ImageView = itemView.findViewById(R.id.yourImage)
    private var namePlatList: TextView = itemView.findViewById(R.id.playerPlayLists)
    private var numberOfTrack: TextView = itemView.findViewById(R.id.playerNumberOfTracks)

    fun bind(model: PlayList) {
        val transformation = MultiTransformation(CenterCrop(), RoundedCorners(10))
        Glide.with(itemView).load(model.image)
            .placeholder(R.drawable.ic_placeholder).centerCrop()
            .transform(transformation)
            .into(image)
        namePlatList.text = model.name

        numberOfTrack.text = getNumber(model)
    }

    fun getNumber(model: PlayList): String {
        if (model.numberTracks != null) {
            return "${model.numberTracks}"
        } else {
            return "0 трэков"
        }
    }
}