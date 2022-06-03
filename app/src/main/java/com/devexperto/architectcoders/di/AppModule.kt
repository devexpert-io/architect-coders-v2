package com.devexperto.architectcoders.di

import android.app.Application
import androidx.room.Room
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.SchedulerProvider
import com.devexperto.architectcoders.data.AndroidPermissionChecker
import com.devexperto.architectcoders.data.PermissionChecker
import com.devexperto.architectcoders.data.PlayServicesLocationDataSource
import com.devexperto.architectcoders.data.database.MovieDatabase
import com.devexperto.architectcoders.data.database.MovieRoomDataSource
import com.devexperto.architectcoders.data.datasource.LocationDataSource
import com.devexperto.architectcoders.data.datasource.MovieLocalDataSource
import com.devexperto.architectcoders.data.datasource.MovieRemoteDataSource
import com.devexperto.architectcoders.data.server.MovieServerDataSource
import com.devexperto.architectcoders.data.server.RemoteService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey(app: Application): String = app.getString(R.string.api_key)

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app,
        MovieDatabase::class.java,
        "movie-db"
    ).build()

    @Provides
    @Singleton
    fun provideMovieDao(db: MovieDatabase) = db.movieDao()

    @Provides
    @Singleton
    @ApiUrl
    fun provideApiUrl(): String = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(this).build()
    }

    @Provides
    @Singleton
    fun provideRemoteService(@ApiUrl apiUrl: String, okHttpClient: OkHttpClient): RemoteService {

        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideSchedulerProvider() = object : SchedulerProvider {
        override fun ui(): Scheduler = AndroidSchedulers.mainThread()

        override fun computation(): Scheduler = Schedulers.computation()

        override fun io(): Scheduler = Schedulers.io()
    }

}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {

    @Binds
    abstract fun bindLocalDataSource(localDataSource: MovieRoomDataSource): MovieLocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: MovieServerDataSource): MovieRemoteDataSource

    @Binds
    abstract fun bindLocationDataSource(locationDataSource: PlayServicesLocationDataSource): LocationDataSource

    @Binds
    abstract fun bindPermissionChecker(permissionChecker: AndroidPermissionChecker): PermissionChecker

}