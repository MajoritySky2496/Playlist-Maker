package com.example.soundhaven.playlist.mediateca.presentation.model

import com.example.soundhaven.playlist.playlist.domain.models.PlayList

interface PlayListsScreenState {
    data class showPlayLists(val playLists:List<PlayList>):PlayListsScreenState
}