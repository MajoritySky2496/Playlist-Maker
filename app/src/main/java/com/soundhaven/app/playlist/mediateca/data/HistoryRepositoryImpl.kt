package com.soundhaven.app.playlist.mediateca.data

import com.soundhaven.app.R
import com.soundhaven.app.playlist.mediateca.data.converters.TrackDbConvertor
import com.soundhaven.app.playlist.database.db.AppDatabase
import com.soundhaven.app.playlist.database.db.entity.TrackEntity
import com.soundhaven.app.playlist.mediateca.domain.HistoryRepository
import com.soundhaven.app.playlist.search.data.api.ResourceProvider
import com.soundhaven.app.playlist.search.domain.models.Track
import com.soundhaven.app.playlist.util.Resource
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