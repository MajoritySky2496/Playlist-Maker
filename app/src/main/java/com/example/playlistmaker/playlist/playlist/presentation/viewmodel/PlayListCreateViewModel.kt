package com.example.playlistmaker.playlist.playlist.presentation.viewmodel

import com.example.playlistmaker.playlist.playlist.domain.PlayListInteractor
import com.example.playlistmaker.playlist.search.data.api.ResourceProvider

class PlayListCreateViewModel(private val interactor: PlayListInteractor, private val resourceProvider: ResourceProvider):PlayListViewModel(interactor, resourceProvider) {

    override fun insertPlayList() {
        super.insertPlayList()
    }




}