package ar.com.jobsity.challenge.network.di

import ar.com.jobsity.challenge.BuildConfig
import ar.com.jobsity.challenge.network.api.TvMazeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    @Named("TvMaze")
    fun provideTvMazeRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor {
                            Timber.i(it)
                        }.setLevel(HttpLoggingInterceptor.Level.BASIC)
                    )
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideTvMazeApi(@Named("TvMaze") tvMazeRetrofit: Retrofit): TvMazeApi {
        return tvMazeRetrofit.create(TvMazeApi::class.java)
    }

}