package com.example.soundhaven.playlist.playlist.ui.models.aboutplaylist

import com.example.soundhaven.playlist.playlist.domain.models.PlayList
import com.example.soundhaven.playlist.search.domain.models.Track

sealed interface AboutPlayListState{

    data class ShowInfOfPlayList(val playList:PlayList?, val track:MutableList<Track>, val trackDuration: String):AboutPlayListState
    object GoBack:AboutPlayListState
}