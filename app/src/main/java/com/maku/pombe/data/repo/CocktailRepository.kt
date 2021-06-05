package com.maku.pombe.data.repo

import com.maku.pombe.data.local.datasource.LocalDataSource
import com.maku.pombe.data.remote.datasource.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class CocktailRepository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
){
    val remote = remoteDataSource
    val local = localDataSource
}