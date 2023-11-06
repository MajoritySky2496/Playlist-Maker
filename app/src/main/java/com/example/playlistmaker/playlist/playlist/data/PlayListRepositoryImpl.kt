package com.example.playlistmaker.playlist.playlist.data


import android.net.Uri
import android.util.Log
import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.database.db.AppDatabase
import com.example.playlistmaker.playlist.playlist.data.converters.PlayListDbConvertor
import com.example.playlistmaker.playlist.database.db.entity.PlayListEntity
import com.example.playlistmaker.playlist.database.db.entity.PlayListTrackEntity
import com.example.playlistmaker.playlist.playlist.data.storage.Storage
import com.example.playlistmaker.playlist.playlist.domain.PlayListRepository
import com.example.playlistmaker.playlist.playlist.domain.models.PlayList
import com.example.playlistmaker.playlist.search.data.api.ResourceProvider
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map

class PlayListRepositoryImpl(
    private val appDatabase: AppDatabase,
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

    override suspend fun deletePlayList(idPlayList: Int) {
        appDatabase.playListDao().PlayListsClear(idPlayList)
    }

    override suspend fun saveImageToPrivateStorage(uri: Uri?) {
        privateStorage.saveImageToPrivateStorage(uri)
    }

    override suspend fun getImage(uri: String): Uri {

        return privateStorage.getImage(uri)
    }

    override suspend fun insertTrackPlayList(
        trackEntity: PlayListTrackEntity,
        playList: PlayListEntity
    ) {
        appDatabase.tracksDao().insertTrackPlayList(trackEntity)
        val TrackIdList = mutableListOf<String>()
        playList.idTracks?.let { fromStringToList(it) }
            ?.let { TrackIdList.addAll(it) }
        TrackIdList.add(0, trackEntity.trackId)
        val playListCopy=playList.copy(numberTracks = "${TrackIdList.size.toString()} ${numberOfTracks(TrackIdList)}" , idTracks = fromListToString(TrackIdList))
        appDatabase.playListDao().updatePlayList(playListCopy)

    }
    override suspend fun deleteTrackFromPlayList(
        trackEntity: PlayListTrackEntity,
        playList: PlayListEntity,
        trackList: MutableList<PlayListTrackEntity>
    ) {
        val trackListCopy: MutableList<PlayListTrackEntity> = mutableListOf()

        trackList.map { if (it.trackId != trackEntity.trackId) trackListCopy.add(it) }
        val trackIdList: MutableList<String> = mutableListOf()
        trackListCopy.map { trackIdList.add(it.trackId) }

        val playListCopy = playList.copy(
            numberTracks = "${trackIdList.size} ${
                numberOfTracks(trackIdList)
            }", idTracks = fromListToString(trackIdList)
        )
        appDatabase.playListDao().updatePlayList(playListCopy)
        deleteTrackPlayList(trackEntity)
    }
    suspend fun deleteTrackPlayList(track:PlayListTrackEntity){
        val listOfplayList = appDatabase.playListDao().getPlayLists()
        var deleteCheck = true

        var idTrackList = mutableListOf<String>()
        listOfplayList.map { it.map { idTrackList = fromStringToList(it.idTracks).toMutableList()
        if(idTrackList.contains(track.trackId)) {
            deleteCheck = false
        }
        } }
        if(deleteCheck.equals(true)){
            appDatabase.tracksDao().deleteTrack(track)
        }


    }
    override fun getPlayList(idPlayList: Int?): Flow<PlayList> {
        val playList = appDatabase.playListDao().getPlayList(idPlayList)
        Log.d("playList", "$playList")
        return playList.map { converter.convertToPlayList(it) }

    }

    private suspend fun checkIsFavorite(tracks: List<Track>): List<Track> {
        val tracksIdEntity = appDatabase.trackDao().getTracksId()
        tracksIdEntity.forEach { trackId ->
            tracks.map { if (it.trackId.equals(trackId)) it.isFavorite = true }
        }
        return tracks
    }

    override fun getTrack(trackId: String?): Flow<List<Track>> = flow {
        val trackIdList = mutableListOf<String>()
        trackIdList.addAll(fromStringToList(trackId))
        val tracksEntity = mutableListOf<PlayListTrackEntity>()
        trackIdList.map { tracksEntity.add(appDatabase.tracksDao().getTrack(it)) }
        emit(checkIsFavorite(tracksEntity.map { converter.convertToTrack(it) }))


    }

    override suspend fun udpadePlayList(playList: PlayList) {
        appDatabase.playListDao().updatePlayList(converter.convertToPlayListEntity(playList))
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