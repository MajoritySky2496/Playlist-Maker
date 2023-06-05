package com.example.playlistmaker.playlist.mediateca.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.playlistmaker.databinding.FragmentSelectedtracksBinding
import com.example.playlistmaker.playlist.mediateca.presentation.SelectedTracksViewModel
import com.example.playlistmaker.playlist.mediateca.ui.activity.MediatecaActivity
import com.example.playlistmaker.playlist.util.BindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SelectedTracksFragment:BindingFragment<FragmentSelectedtracksBinding>(){
    private val viewModel:SelectedTracksViewModel by viewModel{
        parametersOf()
    }
    fun newInstance() = SelectedTracksFragment().apply {

    }
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSelectedtracksBinding {
        return FragmentSelectedtracksBinding.inflate(inflater, container, false)
    }
}