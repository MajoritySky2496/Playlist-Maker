package com.example.playlistmaker.player

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.playlistmaker.R
import kotlinx.android.synthetic.main.activity_search.view.history
import java.lang.IllegalArgumentException

class PlayerAdapter :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var trackImage:String? = null
    var trackName:String? = null
    var artistName:String? = null




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType){
            R.layout.player_image_item -> ImageVH(inflater.inflate(viewType, parent, false))
            R.layout.player_trackname_item -> TrackNameVH(inflater.inflate(viewType, parent, false))
            R.layout.player_name_artist_item-> NameArtistVH(inflater.inflate(viewType, parent, false))
            R.layout.player_buttons_item -> ButtonsVH(inflater.inflate(viewType, parent, false))
            R.layout.player_attribute_item -> AttributeVH(inflater.inflate(viewType, parent, false))
            else -> throw IllegalArgumentException("Error")
        }

    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ImageVH-> {
                Glide.with(holder.itemView.context)
                    .load(trackImage!!.replaceAfterLast('/', "512x512bb.jpg"))
                    .placeholder(R.drawable.ic_placeholder_mediateca)
                    .apply((RequestOptions.bitmapTransform(RoundedCorners(holder.itemView.resources.getDimensionPixelSize(R.dimen.radius_image_track)))))
                    .into(holder.itemView as ImageView)
            }
            is TrackNameVH -> (holder.itemView as TextView).text = trackName
            is NameArtistVH -> (holder.itemView as TextView).text = artistName
        }
    }


    override fun getItemCount(): Int {
        return 9
    }

    override fun getItemViewType(position: Int): Int {
        return when (position){
            0 -> R.layout.player_image_item
            1 -> R.layout.player_trackname_item
            2 -> R.layout.player_name_artist_item
            3 -> R.layout.player_buttons_item
            in 4..9  -> R.layout.player_attribute_item
            else -> throw IllegalArgumentException("Error")


        }
    }

    class ImageVH(itemView: View) : RecyclerView.ViewHolder(itemView)
    class TrackNameVH(itemView: View):RecyclerView.ViewHolder(itemView)
    class NameArtistVH(itemView: View):RecyclerView.ViewHolder(itemView)
    class ButtonsVH(itemView: View):RecyclerView.ViewHolder(itemView){

        val addPlayList = itemView.findViewById<View>(R.id.addPlayList)
        val play = itemView.findViewById<View>(R.id.play)
        val like = itemView.findViewById<View>(R.id.like)
        val time = itemView.findViewById<TextView>(R.id.textView2)


    }
    class AttributeVH(itemView: View):RecyclerView.ViewHolder(itemView){
        val attKey = itemView.findViewById<TextView>(R.id.attribute_name)
        val attValue = itemView.findViewById<TextView>(R.id.attribute_value)
    }








}