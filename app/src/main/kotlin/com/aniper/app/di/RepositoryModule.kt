package com.aniper.app.di

import com.aniper.app.data.local.db.dao.CharacterDao
import com.aniper.app.data.local.prefs.AppPreferences
import com.aniper.app.data.remote.api.MarketApiService
import com.aniper.app.data.repository.CharacterRepository
import com.aniper.app.data.repository.MarketRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideCharacterRepository(
        characterDao: CharacterDao,
        appPreferences: AppPreferences
    ): CharacterRepository {
        return CharacterRepository(characterDao, appPreferences)
    }

    @Singleton
    @Provides
    fun provideMarketRepository(
        apiService: MarketApiService
    ): MarketRepository {
        return MarketRepository(apiService)
    }
}
