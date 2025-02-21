package com.soundhaven.app.playlist.mediateca.presentation.model

import com.soundhaven.app.playlist.playlist.domain.models.PlayList

interface PlayListsScreenState {
    data class showPlayLists(val playLists:List<PlayList>):PlayListsScreenState
}