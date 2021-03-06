package com.maku.pombe.di

import android.content.Context
import androidx.room.Room
import com.maku.pombe.data.local.database.CockTailsDatabase
import com.maku.pombe.utils.Constants.Companion.DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        CockTailsDatabase::class.java,
        DATABASE
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: CockTailsDatabase) = database.cocktailsDao()

}