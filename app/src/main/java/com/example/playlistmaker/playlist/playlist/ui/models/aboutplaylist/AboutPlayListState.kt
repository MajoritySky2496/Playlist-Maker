package com.example.playlistmaker.playlist.playlist.ui.models.aboutplaylist

import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import com.example.playlistmaker.playlist.search.domain.models.Track

sealed interface AboutPlayListState{

    data class ShowInfOfPlayList(val playList:PlayList, val track:MutableList<Track>):AboutPlayListState
}