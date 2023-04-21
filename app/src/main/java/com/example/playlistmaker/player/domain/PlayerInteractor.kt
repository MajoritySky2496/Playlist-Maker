package com.example.playlistmaker.player.domain

class PlayerInteractor(private val player:Player) {


    fun subscribeOnPlayer(listener:PlayerStateListener){
        player.listener = listener
    }
    fun unSubscribeOnPlayer(){
        player.listener = null
    }
}