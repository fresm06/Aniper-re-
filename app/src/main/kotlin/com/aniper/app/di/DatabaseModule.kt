package com.aniper.app.di

import android.content.Context
import com.aniper.app.data.local.db.AnIperDatabase
import com.aniper.app.data.local.db.dao.CharacterDao
import com.aniper.app.data.local.prefs.AppPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAnIperDatabase(@ApplicationContext context: Context): AnIperDatabase {
        return AnIperDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideCharacterDao(database: AnIperDatabase): CharacterDao {
        return database.characterDao()
    }

    @Singleton
    @Provides
    fun provideAppPreferences(@ApplicationContext context: Context): AppPreferences {
        return AppPreferences(context)
    }
}
