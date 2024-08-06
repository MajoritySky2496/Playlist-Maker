package com.example.soundhaven.playlist.di

import android.content.Context
import androidx.room.Room
import com.example.soundhaven.playlist.database.db.AppDatabase
import com.example.soundhaven.playlist.playlist.data.PrivateStorage
import com.example.soundhaven.playlist.playlist.data.storage.Storage
import com.example.soundhaven.playlist.search.data.NetworkClient
import com.example.soundhaven.playlist.search.data.TrackStorage
import com.example.soundhaven.playlist.search.data.localwork.SharedPrefsStorage
import com.example.soundhaven.playlist.search.data.localwork.SharedPrefsStorage.Companion.FALSE
import com.example.soundhaven.playlist.search.data.network.ItunesApiService
import com.example.soundhaven.playlist.search.data.network.LyricsApiService
import com.example.soundhaven.playlist.search.data.network.RetrofitNetworkClient
import com.example.soundhaven.playlist.search.data.network.RetrofitNetworkClient.Companion.BASE_URL
import com.example.soundhaven.playlist.settings.data.impl.SettingSharedPrefsStorage
import com.example.soundhaven.playlist.settings.data.impl.SettingStorage
import com.example.soundhaven.playlist.sharing.data.ExternalNavigator
import com.example.soundhaven.playlist.sharing.data.impl.ExternalNavigatorImpl
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
    single<LyricsApiService> {
        Retrofit.Builder().baseUrl("https://api.lyrics.ovh")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(LyricsApiService::class.java)
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
        RetrofitNetworkClient(get(), get(), androidContext())
    }
    single<ExternalNavigator> {
        ExternalNavigatorImpl(androidContext())
    }
    single<SettingStorage> { SettingSharedPrefsStorage(get()) }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()

    }
    single<Storage> {PrivateStorage(androidContext())  }

}