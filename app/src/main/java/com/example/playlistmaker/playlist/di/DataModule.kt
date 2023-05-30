package com.example.playlistmaker.playlist.di

import android.content.Context
import com.example.playlistmaker.playlist.search.data.NetworkClient
import com.example.playlistmaker.playlist.search.data.TrackStorage
import com.example.playlistmaker.playlist.search.data.localwork.SharedPrefsStorage
import com.example.playlistmaker.playlist.search.data.localwork.SharedPrefsStorage.Companion.FALSE
import com.example.playlistmaker.playlist.search.data.network.ItunesApiService
import com.example.playlistmaker.playlist.search.data.network.RetrofitNetworkClient
import com.example.playlistmaker.playlist.search.data.network.RetrofitNetworkClient.Companion.BASE_URL
import com.example.playlistmaker.playlist.settings.data.impl.SettingSharedPrefsStorage
import com.example.playlistmaker.playlist.settings.data.impl.SettingStorage
import com.example.playlistmaker.playlist.sharing.data.ExternalNavigator
import com.example.playlistmaker.playlist.sharing.data.impl.ExternalNavigatorImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single<ItunesApiService> {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ItunesApiService::class.java)
    }

    single {
        androidContext()
            .getSharedPreferences(
                FALSE, Context.MODE_PRIVATE
            )
    }

    single<TrackStorage> {
        SharedPrefsStorage(get())
    }
    single<NetworkClient> {
        RetrofitNetworkClient(get(), androidContext())
    }
    single<ExternalNavigator> {
        ExternalNavigatorImpl(androidContext())
    }
    single<SettingStorage> { SettingSharedPrefsStorage(get()) }


}