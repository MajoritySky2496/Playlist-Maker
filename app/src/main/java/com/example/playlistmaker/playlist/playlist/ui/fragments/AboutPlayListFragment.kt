package com.example.playlistmaker.playlist.playlist.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.playlistmaker.databinding.FragmentAboutPlaylistBinding
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import com.example.playlistmaker.playlist.util.BindingFragment

class AboutPlayListFragment:BindingFragment<FragmentAboutPlaylistBinding>() {
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAboutPlaylistBinding {
        return FragmentAboutPlaylistBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idPlayList = arguments?.getInt("amount")
        val q = idPlayList





    }
}