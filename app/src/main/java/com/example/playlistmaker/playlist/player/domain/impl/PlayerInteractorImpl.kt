package com.example.playlistmaker.playlist.player.domain.impl

import com.example.playlistmaker.playlist.player.domain.api.PlayerInteractor
import com.example.playlistmaker.playlist.player.domain.api.MediaPlayerRepository
import com.example.playlistmaker.playlist.playlist.domain.PlayListRepository
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import kotlinx.coroutines.flow.Flow

class PlayerInteractorImpl(private val player:MediaPlayerRepository):PlayerInteractor {

    override fun startPlayer(statusObserver: PlayerInteractor.StatusObserver) {
        player.startPlayer()
        statusObserver.onPlay()
    }
    override fun pausePlayer() {
        player.pausePlayer()
    }
    override fun stopPlayer(){
        player.stopPlayer()
    }

    override fun getCurrentPosition(): Int {
        return player.getCurrentPosition()
    }

    override fun preparePlayer(url:String) {
        player.preparePlayer(url)
    }

    override fun release(){
        player.release()
    }

    override fun setOnPreparedListener(listener: (Any) -> Unit){
        player.setOnPreparedListener(listener)

    }
    override fun setOnCompletionListener(listener: (Any) -> Unit){
        player.setOnCompletionListener(listener)
    }




}