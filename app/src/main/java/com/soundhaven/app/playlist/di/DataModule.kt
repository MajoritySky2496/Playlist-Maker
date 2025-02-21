package com.soundhaven.app.playlist.di

import android.content.Context
import androidx.room.Room
import com.soundhaven.app.playlist.database.db.AppDatabase
import com.soundhaven.app.playlist.playlist.data.PrivateStorage
import com.soundhaven.app.playlist.playlist.data.storage.Storage
import com.soundhaven.app.playlist.search.data.NetworkClient
import com.soundhaven.app.playlist.search.data.TrackStorage
import com.soundhaven.app.playlist.search.data.localwork.SharedPrefsStorage
import com.soundhaven.app.playlist.search.data.localwork.SharedPrefsStorage.Companion.FALSE
import com.soundhaven.app.playlist.search.data.network.ItunesApiService
import com.soundhaven.app.playlist.search.data.network.LyricsApiService
import com.soundhaven.app.playlist.search.data.network.RetrofitNetworkClient
import com.soundhaven.app.playlist.search.data.network.RetrofitNetworkClient.Companion.BASE_URL
import com.soundhaven.app.playlist.settings.data.impl.SettingSharedPrefsStorage
import com.soundhaven.app.playlist.settings.data.impl.SettingStorage
import com.soundhaven.app.playlist.sharing.data.ExternalNavigator
import com.soundhaven.app.playlist.sharing.data.impl.ExternalNavigatorImpl
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