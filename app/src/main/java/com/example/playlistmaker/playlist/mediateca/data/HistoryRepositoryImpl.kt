package com.example.playlistmaker.playlist.mediateca.data

import com.example.playlistmaker.R
import com.example.playlistmaker.playlist.mediateca.data.converters.TrackDbConvertor
import com.example.playlistmaker.playlist.database.db.AppDatabase
import com.example.playlistmaker.playlist.database.db.entity.TrackEntity
import com.example.playlistmaker.playlist.mediateca.domain.HistoryRepository
import com.example.playlistmaker.playlist.search.data.api.ResourceProvider
import com.example.playlistmaker.playlist.search.domain.models.Track
import com.example.playlistmaker.playlist.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HistoryRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val trackDbConvertor: TrackDbConvertor,
    private val resourceProvider: ResourceProvider

):HistoryRepository {
    override suspend fun insertTrack(track: Track) {
        appDatabase.trackDao().insertTrack(trackDbConvertor.map(track))
    }

    override suspend fun deleteTrack(track: Track) {
        appDatabase.trackDao().deleteTrack(trackDbConvertor.map(track))
    }

    override fun historyTrack(): Flow<Resource<List<Track>?>> = flow {

        val tracksEntity = appDatabase.trackDao().getTracks()
        if(tracksEntity.isEmpty()){
            emit(Resource.Error(resourceProvider.getString(R.string.Mediateca_is_empty)))
        }else{
            val tracks = convertFromTrackEntity(tracksEntity)
            tracks.forEach{
                it.isFavorite = true
            }
            emit(Resource.Success(tracks))

        }

    }
    private fun convertFromTrackEntity(tracks:List<TrackEntity>):List<Track>{
        return tracks.map { track -> trackDbConvertor.map(track) }
    }

}