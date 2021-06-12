package com.maku.pombe.vm

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.maku.pombe.data.local.entities.LatestCocktailsEntity
import com.maku.pombe.data.local.entities.PopularCocktailsEntity
import com.maku.pombe.data.local.entities.RecentCocktailsEntity
import com.maku.pombe.data.models.latest.Latest
import com.maku.pombe.data.models.popular.Popular
import com.maku.pombe.data.models.recent.Drink
import com.maku.pombe.data.repo.CocktailRepository
import com.maku.pombe.data.models.recent.Recent
import com.maku.pombe.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: CocktailRepository,
    application: Application)
    : AndroidViewModel(application) {

    /** ROOM DATABASE */
    val readLatestCocktails: LiveData<List<LatestCocktailsEntity>> = repository.local.readLatestCocktails().asLiveData()

    private fun insertLatestCocktails(latestCocktailsEntity: LatestCocktailsEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertLatestCocktails(latestCocktailsEntity)
        }

    val readPopularCocktails: LiveData<List<PopularCocktailsEntity>> = repository.local.readPopularCocktails().asLiveData()

    private fun insertRecentCocktails(popularCocktailsEntity: PopularCocktailsEntity) =
            viewModelScope.launch(Dispatchers.IO) {
                repository.local.insertPopularCocktails(popularCocktailsEntity)
            }

    val readRecentCocktails: LiveData<List<RecentCocktailsEntity>> = repository.local.readRecentCocktails().asLiveData()

    private fun insertRecentCocktails(recentCocktailsEntity: RecentCocktailsEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecentCocktails(recentCocktailsEntity)
        }

    /** RETROFIT */
    var latestCocktailResponse: MutableLiveData<NetworkResult<Latest>> = MutableLiveData()

    fun getLatestCocktails() = viewModelScope.launch {
        getLatestCocktailsSafeCall()
    }

    var popularCocktailResponse: MutableLiveData<NetworkResult<Popular>> = MutableLiveData()

    fun getPopularCocktails() = viewModelScope.launch {
        getPopularCocktailsSafeCall()
    }

    var recentCocktailResponse: MutableLiveData<NetworkResult<Recent>> = MutableLiveData()

    fun getRecentCocktails() = viewModelScope.launch {
        getRecentCocktailsSafeCall()
    }

    // latest
    private suspend fun getLatestCocktailsSafeCall() {
        latestCocktailResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getLatestCocktails()
                Timber.d("response$response")
                latestCocktailResponse.value = handleLatestCocktailResponse(response)

                // offline
                val latestCocktails = latestCocktailResponse.value!!.data
                if(latestCocktails != null) {
                    offlineCacheLatestCocktails(latestCocktails)
                }

            } catch (e: Exception) {
                popularCocktailResponse.value = NetworkResult.Error("Recent cocktails not found.")
            }
        } else {
            popularCocktailResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun offlineCacheLatestCocktails(latestCocktails: Latest) {
        val latestEntity = LatestCocktailsEntity(latestCocktails)
        insertLatestCocktails(latestEntity)
    }

    private fun handleLatestCocktailResponse(response: Response<Latest>): NetworkResult<Latest>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()!!.drinks.isNullOrEmpty() -> {
                return NetworkResult.Error("Cocktails not found.")
            }
            response.isSuccessful -> {
                val latestCocktails = response.body()
                return NetworkResult.Success(latestCocktails!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    // popular
    private suspend fun getPopularCocktailsSafeCall() {
        popularCocktailResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getPopularCocktails()
                Timber.d("response$response")
                popularCocktailResponse.value = handlePopularCocktailResponse(response)

                // offline
                val recentPopularCocktails = popularCocktailResponse.value!!.data
                if(recentPopularCocktails != null) {
                    offlineCachePopularCocktails(recentPopularCocktails)
                }

            } catch (e: Exception) {
                popularCocktailResponse.value = NetworkResult.Error("Recent cocktails not found.")
            }
        } else {
            popularCocktailResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun offlineCachePopularCocktails(popular: Popular) {
        val popularEntity = PopularCocktailsEntity(popular)
        insertRecentCocktails(popularEntity)
    }

    private fun handlePopularCocktailResponse(response: Response<Popular>): NetworkResult<Popular>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()!!.drinks.isNullOrEmpty() -> {
                return NetworkResult.Error("Cocktails not found.")
            }
            response.isSuccessful -> {
                val popularCocktails = response.body()
                return NetworkResult.Success(popularCocktails!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }


    // recent
    private suspend fun getRecentCocktailsSafeCall() {
        recentCocktailResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getRecentCocktails()
                Timber.d("response$response")
                recentCocktailResponse.value = handleRecentCocktailResponse(response)

                // offline
                val recentRecentCocktails = recentCocktailResponse.value!!.data
                if(recentRecentCocktails != null) {
                    offlineCacheRecentCocktails(recentRecentCocktails)
                }

            } catch (e: Exception) {
                recentCocktailResponse.value = NetworkResult.Error("Recent cocktails not found.$e")
            }
        } else {
            recentCocktailResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun offlineCacheRecentCocktails(recent: Recent) {
        val recentEntity = RecentCocktailsEntity(recent)
        insertRecentCocktails(recentEntity)
    }

    private fun handleRecentCocktailResponse(response: Response<Recent>): NetworkResult<Recent>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()!!.drinks.isNullOrEmpty() -> {
                return NetworkResult.Error("Cocktails not found.")
            }
            response.isSuccessful -> {
                val foodRecipes = response.body()
                return NetworkResult.Success(foodRecipes!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }



}