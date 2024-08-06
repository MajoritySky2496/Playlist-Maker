package com.example.soundhaven.playlist.player.ui.models

import com.example.soundhaven.playlist.playlist.domain.models.PlayList

sealed interface BottomSheetScreenState{

    data class ShowPlayLists(val playLists:List<PlayList>):BottomSheetScreenState
    data class CloseBottomSheet(val playList: PlayList):BottomSheetScreenState

}