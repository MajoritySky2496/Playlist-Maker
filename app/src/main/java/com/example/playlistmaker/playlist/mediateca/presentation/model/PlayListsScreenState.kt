package com.example.playlistmaker.playlist.mediateca.presentation.model

import com.example.playlistmaker.playlist.playlist.domain.models.PlayList

interface PlayListsScreenState {

    data class showPlayLists(val playLists:List<PlayList>):PlayListsScreenState

}