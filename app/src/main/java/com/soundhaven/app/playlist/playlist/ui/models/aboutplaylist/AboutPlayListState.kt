package com.soundhaven.app.playlist.playlist.ui.models.aboutplaylist

import com.soundhaven.app.playlist.playlist.domain.models.PlayList
import com.soundhaven.app.playlist.search.domain.models.Track

sealed interface AboutPlayListState{

    data class ShowInfOfPlayList(val playList:PlayList?, val track:MutableList<Track>, val trackDuration: String):AboutPlayListState
    object GoBack:AboutPlayListState
}