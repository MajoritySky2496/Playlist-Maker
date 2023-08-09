package com.example.playlistmaker.playlist.playlist.data

import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.search.data.api.ResourceProvider



import android.net.Uri
import com.example.playlistmaker.playlist.mediateca.data.db.TrackEntity
import com.example.playlistmaker.playlist.playlist.data.converters.PlayListDbConvertor
import com.example.playlistmaker.playlist.playlist.data.db.AppDatabasePlayList
import com.example.playlistmaker.playlist.playlist.data.db.PlayListEntity
import com.example.playlistmaker.playlist.playlist.data.storage.Storage
import com.example.playlistmaker.playlist.playlist.domain.PlayListRepository
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlayListRepositoryImpl(private val appDatabase: AppDatabasePlayList,
                             private val converter:PlayListDbConvertor,
                            private val privateStorage: Storage,
                             private val resourceProvider: ResourceProvider



):PlayListRepository {
    override suspend fun insertPlayList(playList: PlayListEntity ) {
        appDatabase.playListDao().insertPlayList(playList)

    }

    override suspend fun getPlayLists(): Flow<List<PlayList>> {


       return convertToPlayList(appDatabase.playListDao().getPlayLists())

    }

    override suspend fun deletePlayList(playList: PlayListEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun saveImageToPrivateStorage(uri: Uri?) {
        privateStorage.saveImageToPrivateStorage(uri)
    }

    override suspend fun getImage(uri: String): Uri {

        return privateStorage.getImage(uri)
    }

    override suspend fun insertTrackPlayList(trackEntity: TrackEntity, playList: PlayListEntity) {
        appDatabase.tracksDao().insertTrackPlayList(trackEntity)
        val TrackIdList = mutableListOf<String>()
        playList.idTracks?.let { fromStringToList(it) }
            ?.let { TrackIdList.addAll(it) }
        TrackIdList.add(trackEntity.trackId)
        val playListCopy=playList.copy(numberTracks = "${TrackIdList.size.toString()} ${numberOfTracks(TrackIdList)}" , idTracks = fromListToString(TrackIdList))
        appDatabase.playListDao().updatePlayList(playListCopy)
    }
    private fun numberOfTracks(track:MutableList<String>):String{
        when(track.size){

            1 -> return " ${resourceProvider.getString(R.string.track)}"
            2, 3, 4 -> return " ${resourceProvider.getString(R.string.tracka)}"
            else -> return " ${resourceProvider.getString(R.string.tracks)}"


        }
    }

    private fun convertToPlayList(playListEntity:Flow<List<PlayListEntity>>):Flow<List<PlayList>>{
        return playListEntity.map { listOfPlayList -> listOfPlayList.map { playList->  converter.map(playList) }  }

    }
    fun fromListToString(list: List<String>): String {
        val json = Gson().toJson(list)
        return json
    }


    fun fromStringToList(value: String): List<String> {
        val json = Gson().fromJson(value, Array<String>::class.java)
        return json.toList()
    }


}