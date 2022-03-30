package com.maku.pombe.common.data.di

import android.content.Context
import androidx.room.Room
import com.maku.pombe.common.data.cache.Cache
import com.maku.pombe.common.data.cache.PombeDatabase
import com.maku.pombe.common.data.cache.RoomCache
import com.maku.pombe.common.data.cache.daos.PopularDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {

  @Binds
  abstract fun bindCache(cache: RoomCache): Cache

  companion object {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): PombeDatabase {
      return Room.databaseBuilder(
          context,
          PombeDatabase::class.java,
          "pombe.db"
      )
          .build()
    }

    @Provides
    fun provideAnimalsDao(
        pombeDatabase: PombeDatabase
    ): PopularDao = pombeDatabase.popularDao()

    @Provides
    fun provideOrganizationsDao( pombeDatabase: PombeDatabase): PopularDao =
        pombeDatabase.popularDao()
  }
}