package com.example.playlistmaker.playlist.mediateca.ui.fragments

import android.os.Bundle
import android.view.Gravity.apply
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat.apply
import com.example.playlistmaker.databinding.FragmentPlaylistBinding
import com.example.playlistmaker.playlist.mediateca.presentation.PlayListViewModel
import com.example.playlistmaker.playlist.mediateca.ui.activity.MediatecaActivity.Companion.TRACK_ID
import com.example.playlistmaker.playlist.util.BindingFragment
import javax.xml.xpath.XPathConstants.NUMBER
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PlayListFragment:BindingFragment<FragmentPlaylistBinding>() {
    private val viewModel: PlayListViewModel by viewModel{
        parametersOf()
    }
    fun newInstance() = PlayListFragment().apply {

    }
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPlaylistBinding {
        return FragmentPlaylistBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}