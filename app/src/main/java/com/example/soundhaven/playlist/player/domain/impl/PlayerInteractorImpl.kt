package com.example.soundhaven.playlist.player.domain.impl

import com.example.soundhaven.playlist.player.domain.PlayerRepository
import com.example.soundhaven.playlist.player.domain.api.MediaPlayerRepository
import com.example.soundhaven.playlist.player.domain.api.PlayerInteractor
import kotlinx.coroutines.flow.Flow

class PlayerInteractorImpl(
    private val player: MediaPlayerRepository,
    private val repository: PlayerRepository
) : PlayerInteractor {

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

    override fun getTrackText(artist: String, title: String): Flow<String> {
        return repository.getTextTrack(artist, title)
    }
}