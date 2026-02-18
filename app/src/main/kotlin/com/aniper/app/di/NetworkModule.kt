package com.aniper.app.di

import com.aniper.app.data.remote.api.MarketApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideJsonSerializer(): Json {
        return Json {
            isLenient = true
            ignoreUnknownKeys = true
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        json: Json,
        @Named("useMock") useMock: Boolean
    ): Retrofit {
        val baseUrl = if (useMock) "http://mock.api/" else "https://api.aniper.app/"

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Singleton
    @Provides
    fun provideMarketApiService(retrofit: Retrofit): MarketApiService {
        return retrofit.create(MarketApiService::class.java)
    }

    @Singleton
    @Provides
    @Named("useMock")
    fun provideUseMock(): Boolean = true // Set to false when real API is available
}
