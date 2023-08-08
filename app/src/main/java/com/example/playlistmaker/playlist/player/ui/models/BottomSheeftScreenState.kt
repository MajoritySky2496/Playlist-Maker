package com.example.playlistmaker.playlist.player.ui.models

import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import com.google.android.material.bottomsheet.BottomSheetBehavior

sealed interface BottomSheetScreenState{

    data class ShowPlayLists(val playLists:List<PlayList>):BottomSheetScreenState
    data class CloseBottomSheet(val playList: PlayList):BottomSheetScreenState

}