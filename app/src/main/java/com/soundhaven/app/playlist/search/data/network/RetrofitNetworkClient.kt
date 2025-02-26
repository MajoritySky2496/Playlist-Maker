package com.soundhaven.app.playlist.search.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.soundhaven.app.playlist.search.data.NetworkClient
import com.soundhaven.app.playlist.search.data.dto.Response
import com.soundhaven.app.playlist.search.data.dto.TrackSearchRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class RetrofitNetworkClient @Inject constructor(
    private val itunesApiService: ItunesApiService,
    private val lyricsApiService: LyricsApiService,
    private val context: Context
): NetworkClient {


    override suspend fun doRequest(dto: Any): Response {
        if (isConnected() == false) {
            return Response().apply { resultCode = -1 }
        }
        if (dto !is TrackSearchRequest) {
            return Response().apply { resultCode = 400 }
        }
        return withContext(Dispatchers.IO){
            try {
                val responce = itunesApiService.search(dto.expression)
                responce.apply { resultCode = 200 }
            } catch (e: Throwable) {
                 Response().apply { resultCode = 500 }

            }
        }

    }


    override suspend fun getTrackText(artist: String, title: String): Response {
        if (isConnected() == false) {
            return Response().apply { resultCode = -1 }
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = lyricsApiService.getTrackText(artist, title)
                response.apply { resultCode = 200 }
            } catch (e: HttpException) {
                Log.d("textTrack", e.response()?.errorBody().toString())
                Response().apply { resultCode = 500 }
            } catch (e: Throwable) {
                Response().apply { resultCode = 500 }
            }
        }


    }


    private fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
    companion object{
        const val BASE_URL = "https://itunes.apple.com"
    }
}