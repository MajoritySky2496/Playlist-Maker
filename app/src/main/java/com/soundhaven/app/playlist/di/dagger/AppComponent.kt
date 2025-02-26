package com.soundhaven.app.playlist.di.dagger

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.soundhaven.app.playlist.database.db.AppDatabase
import com.soundhaven.app.playlist.domain.settingsDomain.impl.SettingsInteractorImpl
import com.soundhaven.app.playlist.main.ui.RootActivity
import com.soundhaven.app.playlist.mediateca.data.HistoryRepositoryImpl
import com.soundhaven.app.playlist.mediateca.data.converters.TrackDbConvertor
import com.soundhaven.app.playlist.mediateca.domain.HistoryInteractor
import com.soundhaven.app.playlist.mediateca.domain.HistoryRepository
import com.soundhaven.app.playlist.mediateca.domain.Impl.HistoryInteractorImpl
import com.soundhaven.app.playlist.mediateca.presentation.PlayListsViewModel
import com.soundhaven.app.playlist.mediateca.presentation.SelectedTracksViewModel
import com.soundhaven.app.playlist.mediateca.ui.fragments.PlayListsFragment
import com.soundhaven.app.playlist.mediateca.ui.fragments.SelectedTracksFragment
import com.soundhaven.app.playlist.player.data.PlayerRepositoryImpl
import com.soundhaven.app.playlist.player.data.TracksMediaPlayer
import com.soundhaven.app.playlist.player.domain.PlayerRepository
import com.soundhaven.app.playlist.player.domain.api.MediaPlayerRepository
import com.soundhaven.app.playlist.player.domain.api.PlayerInteractor
import com.soundhaven.app.playlist.player.domain.impl.PlayerInteractorImpl
import com.soundhaven.app.playlist.player.presentation.PlayerViewModel
import com.soundhaven.app.playlist.player.ui.PlayerActivity
import com.soundhaven.app.playlist.playlist.data.PlayListRepositoryImpl
import com.soundhaven.app.playlist.playlist.data.PrivateStorage
import com.soundhaven.app.playlist.playlist.data.converters.PlayListDbConvertor
import com.soundhaven.app.playlist.playlist.data.storage.Storage
import com.soundhaven.app.playlist.playlist.domain.PlayListInteractor
import com.soundhaven.app.playlist.playlist.domain.PlayListRepository
import com.soundhaven.app.playlist.playlist.domain.impl.PlayListInteractorImpl
import com.soundhaven.app.playlist.playlist.presentation.viewmodel.AboutPlayListViewModel
import com.soundhaven.app.playlist.playlist.presentation.viewmodel.PlayListRedactorViewModel
import com.soundhaven.app.playlist.playlist.presentation.viewmodel.PlayListViewModel
import com.soundhaven.app.playlist.search.data.NetworkClient
import com.soundhaven.app.playlist.search.data.ResourceProviderImpl
import com.soundhaven.app.playlist.search.data.TrackRepositoryImpl
import com.soundhaven.app.playlist.search.data.TrackStorage
import com.soundhaven.app.playlist.search.data.api.ResourceProvider
import com.soundhaven.app.playlist.search.data.localwork.SharedPrefsStorage
import com.soundhaven.app.playlist.search.data.network.ItunesApiService
import com.soundhaven.app.playlist.search.data.network.LyricsApiService
import com.soundhaven.app.playlist.search.data.network.RetrofitNetworkClient
import com.soundhaven.app.playlist.search.data.network.RetrofitNetworkClient.Companion.BASE_URL
import com.soundhaven.app.playlist.search.domain.TrackSearchInteractor
import com.soundhaven.app.playlist.search.domain.TracksRepository
import com.soundhaven.app.playlist.search.domain.impl.TracksSearchInteractorImpl
import com.soundhaven.app.playlist.search.domain.models.Track
import com.soundhaven.app.playlist.search.presentation.TracksSearchViewModel
import com.soundhaven.app.playlist.settings.data.impl.SettingSharedPrefsStorage
import com.soundhaven.app.playlist.settings.data.impl.SettingStorage
import com.soundhaven.app.playlist.settings.data.impl.SettingsRepositoryImpl
import com.soundhaven.app.playlist.settings.domain.api.SettingsInteractor
import com.soundhaven.app.playlist.settings.domain.api.SettingsRepository
import com.soundhaven.app.playlist.settings.presentation.SettingsViewModel
import com.soundhaven.app.playlist.sharing.data.ExternalNavigator
import com.soundhaven.app.playlist.sharing.data.impl.ExternalNavigatorImpl
import com.soundhaven.app.playlist.sharing.domain.SharingInteractor
import com.soundhaven.app.playlist.sharing.domain.impl.SharingInteractorImpl
import dagger.Binds
import dagger.Component
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.assisted.AssistedFactory
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass


@Component(modules = [AppModule::class, NetworkModule::class, RepositoryModule::class, InteractorModule::class, ViewModelModule::class,])
interface AppComponent {

    fun injectRootActivity(application: RootActivity)
    fun injectPlayerActivity(activity: PlayerActivity)
    fun injectSelectedTracksFragment(fragment: SelectedTracksFragment)
    fun injectPlayListFragment(fragment: PlayListsFragment)

    fun viewModelFactory(): ViewModelFactory

}

@Module
class AppModule(private val application: Application) {

    @Provides
    fun provideApplication(): Application = application

    @Provides
    fun provideContext(): Context = application.applicationContext

    @Provides
    fun provideSharedPref(context: Context): SharedPreferences {
        return context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    }

    @Provides
    fun provideTackStorage(context: Context): TrackStorage {
        return SharedPrefsStorage(sharedPrefrs = provideSharedPref(context = context))
    }

    @Provides
    fun provideExternalNavigator(context: Context): ExternalNavigator {
        return ExternalNavigatorImpl(context = context)
    }

    @Provides
    fun provideSettingStorage(context: Context): SettingStorage {
        return SettingSharedPrefsStorage(sharedPrefrs = provideSharedPref(context = context))
    }

    @Provides
    fun provideDataBaseBuilder(context: Context): AppDatabase {
        return Room.databaseBuilder(context = context, AppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideStorage(context: Context): Storage {
        return PrivateStorage(context = context)
    }

    @Provides
    fun provideResourceProvider(context: Context):ResourceProvider{
        return ResourceProviderImpl(context = context)
    }
    @Provides
    fun provideTrack(): Track {
        return Track(
            trackId = "",
            artistName = "",
            trackName = "",
            releaseDate = "",
            primaryGenreName = "",
            country = "",
            collectionName = "",
            artworkUrl100 = "",
            artworkUrl60 = "",
            trackTimeMillis = 0,
            previewUrl = null,
            isFavorite = false
        )
    }

}

@Module
object RepositoryModule {

    @Provides
    fun provideExecutorService(): ExecutorService {
        return Executors.newCachedThreadPool()
    }

    @Provides
    fun provideTrackRepository(
        networkClient: NetworkClient,
        trackStorage: TrackStorage,
        resourceProvider: ResourceProvider,
        appDatabase: AppDatabase
    ): TracksRepository {
        return TrackRepositoryImpl(
            networkClient = networkClient,
            trackStorage = trackStorage,
            resourceProvider = resourceProvider,
            appDatabase = appDatabase
        )
    }

    @Provides
    fun provideMediaPlayerRepository(): MediaPlayerRepository {
        return TracksMediaPlayer()
    }

    @Provides
    fun provideSettingRepository(
        storage: SettingStorage
    ): SettingsRepository {
        return SettingsRepositoryImpl(storage)
    }

    @Provides
    fun provideHistoryRepository(
        appDatabase: AppDatabase,
        trackDbConvertor: TrackDbConvertor,
        resourceProvider: ResourceProvider,
    ): HistoryRepository {
        return HistoryRepositoryImpl(
            appDatabase = appDatabase,
            trackDbConvertor = trackDbConvertor,
            resourceProvider = resourceProvider
        )
    }

    @Provides
    fun provideTrackDbConvertor(): TrackDbConvertor {
        return TrackDbConvertor()
    }

    @Provides
    fun providePlaylistRepository(
        appDatabase: AppDatabase,
        converter: PlayListDbConvertor,
        privateStorage: Storage,
        resourceProvider: ResourceProvider
    ): PlayListRepository {
        return PlayListRepositoryImpl(
            appDatabase = appDatabase,
            converter = converter,
            privateStorage = privateStorage,
            resourceProvider = resourceProvider
        )
    }
    @Provides
    fun providePlayListDbConvertor(): PlayListDbConvertor {
        return PlayListDbConvertor()
    }
    @Provides
    fun providePlayerRepository(
         networkClient: NetworkClient,
         resourceProvider: ResourceProvider
    ): PlayerRepository {
        return PlayerRepositoryImpl(
            networkClient = networkClient,
            resourceProvider = resourceProvider
        )
    }


}

@Module
object InteractorModule{

    @Provides
    fun providePlayerInteractor(
        playerRepository: MediaPlayerRepository,
        repository: PlayerRepository

    ): PlayerInteractor {
        return PlayerInteractorImpl(
            player = playerRepository,
            repository = repository
        )
    }
    @Provides
    fun provideSettingInteractor(
        prefs: SettingsRepository
    ): SettingsInteractor {
        return SettingsInteractorImpl(prefs)
    }
    @Provides
    fun provideSharingInteractor(
        externalNavigator: ExternalNavigator
    ):SharingInteractor{
        return SharingInteractorImpl(externalNavigator)
    }
    @Provides
    fun provideHistoryInteractor(
        repository: HistoryRepository
    ): HistoryInteractor {
        return HistoryInteractorImpl(repository)
    }
    @Provides
    fun providePlayListInteractor(
        playListRepository: PlayListRepository,
        converterPlayList:PlayListDbConvertor,
        converterTrack:TrackDbConvertor
    ): PlayListInteractor {
        return PlayListInteractorImpl(playListRepository, converterPlayList, converterTrack)

    }

    @Provides
    fun provideTrackSearchInteractor(
        repository: TracksRepository
    ):TrackSearchInteractor{
        return TracksSearchInteractorImpl(repository = repository)
    }

}

@Module
object NetworkModule {


    @Provides
    fun provideItunesApiService(): ItunesApiService {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create()

    }

    @Provides
    fun provideLyricsApiService(): LyricsApiService {
        val retrofit = Retrofit.Builder().baseUrl("https://api.lyrics.ovh")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create()
    }


    @Provides
    fun provideNetworkClient(
        context: Context,  // Контекст передаётся сюда
        itunesApiService: ItunesApiService,
        lyricsApiService: LyricsApiService
    ): NetworkClient {
        return RetrofitNetworkClient(itunesApiService, lyricsApiService, context)
    }

}

class ViewModelFactory @Inject constructor(
    private val creators: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?: creators.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("Unknown ViewModel class: $modelClass")

        return try {
            creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}

@AssistedFactory
interface PlayerViewModelFactory {
    fun create(track: Track): PlayerViewModel
}

@Module
abstract class ViewModelModule {



    @Binds
    @IntoMap
    @ViewModelKey(PlayListsViewModel::class)
    abstract fun bindPlayListsViewModel(viewModel: PlayListsViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(TracksSearchViewModel::class)
    abstract fun bindTracksSearchViewModel(viewModel: TracksSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SelectedTracksViewModel::class)
    abstract fun bindSelectedTracksViewModel(viewModel: SelectedTracksViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AboutPlayListViewModel::class)
    abstract fun AboutPlayListViewModel(viewModel: AboutPlayListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlayListViewModel::class)
    abstract fun PlayListViewModel(viewModel: PlayListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlayListRedactorViewModel::class)
    abstract fun PlayListRedactorViewModel(viewModel: PlayListRedactorViewModel): ViewModel



    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun SettingsViewModel(viewModel: SettingsViewModel): ViewModel




    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}


@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)


