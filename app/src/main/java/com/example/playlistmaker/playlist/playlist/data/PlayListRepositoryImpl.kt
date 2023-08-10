package com.example.playlistmaker.playlist.playlist.data

import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.search.data.api.ResourceProvider


import android.net.Uri
import android.util.Log
import com.example.playlistmaker.playlist.mediateca.data.db.TrackEntity
import com.example.playlistmaker.playlist.playlist.data.converters.PlayListDbConvertor
import com.example.playlistmaker.playlist.playlist.data.db.AppDatabasePlayList
import com.example.playlistmaker.playlist.playlist.data.db.PlayListEntity
import com.example.playlistmaker.playlist.playlist.data.storage.Storage
import com.example.playlistmaker.playlist.playlist.domain.PlayListRepository
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class PlayListRepositoryImpl(
    private val appDatabase: AppDatabasePlayList,
    private val converter: PlayListDbConvertor,
    private val privateStorage: Storage,
    private val resourceProvider: ResourceProvider


) : PlayListRepository {
    override suspend fun insertPlayList(playList: PlayListEntity) {
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
        val playListCopy = playList.copy(
            numberTracks = "${TrackIdList.size.toString()} ${
                numberOfTracks(TrackIdList)
            }", idTracks = fromListToString(TrackIdList)
        )
        appDatabase.playListDao().updatePlayList(playListCopy)
    }

    override fun getPlayList(idPlayList: Int?):Flow<PlayList> {
        val playList = appDatabase.playListDao().getPlayList(idPlayList)
        Log.d("playList", "$playList")
        return playList.map { converter.convertToPlayList(it) }

    }

    override fun getTrack(trackId: String?): Flow<List<Track>> = flow {
        val trackIdList = mutableListOf<String>()
        trackIdList.addAll(fromStringToList(trackId))
        val tracksEntity = mutableListOf<TrackEntity>()
        trackIdList.map { tracksEntity.add(appDatabase.tracksDao().getTrack(it)) }
        emit(tracksEntity.map { converter.convertToTrack(it) })

    }

    private fun numberOfTracks(track: MutableList<String>): String {
        when (track.size) {

            1 -> return " ${resourceProvider.getString(R.string.track)}"
            2, 3, 4 -> return " ${resourceProvider.getString(R.string.tracka)}"
            else -> return " ${resourceProvider.getString(R.string.tracks)}"


        }
    }

    private fun convertToPlayList(playListEntity: Flow<List<PlayListEntity>>): Flow<List<PlayList>> {
        return playListEntity.map { listOfPlayList ->
            listOfPlayList.map { playList ->
                converter.convertToPlayList(
                    playList
                )
            }
        }

    }

    fun fromListToString(list: List<String>): String {
        val json = Gson().toJson(list)
        return json
    }


    fun fromStringToList(value: String?): List<String> {
        val json = Gson().fromJson(value, Array<String>::class.java)
        return json.toList()
    }


}