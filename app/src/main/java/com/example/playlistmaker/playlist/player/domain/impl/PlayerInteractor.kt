package com.example.playlistmaker.playlist.player.domain.impl

import com.example.playlistmaker.playlist.player.data.TracksMediaPlayer
import com.example.playlistmaker.playlist.player.domain.api.IPlayerInteractor
import com.example.playlistmaker.playlist.player.domain.api.MediaPlayerRepository
import com.example.playlistmaker.playlist.search.domain.models.Track

class PlayerInteractor(val player:MediaPlayerRepository):IPlayerInteractor {

    override fun startPlayer(statusObserver: IPlayerInteractor.StatusObserver) {
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