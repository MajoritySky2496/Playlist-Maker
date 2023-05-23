package com.example.playlistmaker.playlist.settings.ui.model

sealed interface SwitcherState{
     data class toggle(var theme:Boolean):SwitcherState
}