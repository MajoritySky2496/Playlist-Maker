package com.example.playlistmaker.playlist.playlist.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentAboutPlaylistBinding
import com.example.playlistmaker.playlist.mediateca.presentation.PlayListsViewModel
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import com.example.playlistmaker.playlist.playlist.presentation.viewmodel.AboutPlayListViewModel
import com.example.playlistmaker.playlist.playlist.ui.models.aboutplaylist.AboutPlayListState
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.example.playlistmaker.playlist.search.ui.tracks.TrackAdapter
import com.example.playlistmaker.playlist.util.BindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AboutPlayListFragment:BindingFragment<FragmentAboutPlaylistBinding>() {
    lateinit var recyclerView: RecyclerView
    private val adapter = TrackAdapter{
    }
    private val viewModel: AboutPlayListViewModel by viewModel {
        parametersOf()
    }
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAboutPlaylistBinding {
        return FragmentAboutPlaylistBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idPlayList = arguments?.getInt("idPlayList")

        viewModel.getAboutPlayListStateLiveData().observe(requireActivity()){render(it)}
        viewModel.showScreen(idPlayList)
        recyclerView = binding.recyclerViewTracks
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter


    }
    private fun render(state:AboutPlayListState){
        when(state){
            is AboutPlayListState.ShowInfOfPlayList -> showScreen(state.playList, state.track)
        }
    }
    private fun showScreen(playList: PlayList, tracks:MutableList<Track>){
        binding.playListName.text = playList.name
        binding.description.text = playList.description
        binding.numberTracks.text = playList.numberTracks
        Glide.with(binding.playListImage).load(
            playList.image

        )
            .placeholder(R.drawable.ic_placeholder_mediateca)
            .apply(
                (RequestOptions.bitmapTransform(
                    RoundedCorners(
                        binding.playListImage.resources.getDimensionPixelSize(
                            R.dimen.radius_image_track
                        )
                    )
                ))
            ).into(binding.playListImage)
        adapter.track.clear()
        adapter.track.addAll(tracks)
        adapter.notifyDataSetChanged()

    }
}