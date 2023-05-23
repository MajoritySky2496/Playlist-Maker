package com.example.playlistmaker.playlist.search.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.playlistmaker.playlist.search.data.NetworkClient
import com.example.playlistmaker.playlist.search.data.dto.Response
import com.example.playlistmaker.playlist.search.data.dto.TrackSearchRequest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException

class RetrofitNetworkClient(private val context: Context) : NetworkClient {

    private fun retrofitCreate(): ItunesApiService {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val itunesUrlBase = "https://itunes.apple.com"

        val retrofit = Retrofit.Builder().baseUrl(itunesUrlBase).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ItunesApiService::class.java)
    }

    private val itunesApiService = retrofitCreate()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun doRequest(dto: Any): Response {
        if (isConnected() == false) {
            return Response().apply { resultCode = -1 }
        }
        if (dto !is TrackSearchRequest) {
            return Response().apply { resultCode = 400 }
        }
        try {

            val responce = itunesApiService.search(dto.expression).execute()
            val body = responce.body()
            return if (body != null) {
                return body.apply { resultCode = responce.code() }
            } else {
                Response().apply { resultCode = responce.code() }
            }
        } catch (e: SocketTimeoutException) {
            return Response().apply { resultCode = 400 }

        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
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


}