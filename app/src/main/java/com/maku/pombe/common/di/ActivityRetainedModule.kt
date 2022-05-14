package com.maku.pombe.common.di

import com.maku.pombe.common.data.repostories.PombeRepository
import com.maku.pombe.common.domain.repositories.DrinkRepository
import com.maku.pombe.common.utils.CoroutineDispatchersProvider
import com.maku.pombe.common.utils.DispatchersProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.disposables.CompositeDisposable

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {

  @Binds
  @ActivityRetainedScoped
  /**
  You want to retain the repository when you swap Fragments. You also want it to survive
  configuration changes. To enable this, you add the @ActivityRetainedScoped
  annotation to the binding method. It makes Repository live as
  long as the Activity, surviving configuration changes
   **/
  abstract fun bindPopularDrinkRepository(repository: PombeRepository): DrinkRepository

  @Binds
  abstract fun bindDispatchersProvider(dispatchersProvider: CoroutineDispatchersProvider):
          DispatchersProvider

  companion object {
    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()
  }
}