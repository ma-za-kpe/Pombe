package com.maku.pombe.vm

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.maku.pombe.data.local.entities.RecentCocktailsEntity
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
    val readRecentCocktails: LiveData<List<RecentCocktailsEntity>> = repository.local.readRecentCocktails().asLiveData()

    private fun insertRecentCocktails(recentCocktailsEntity: RecentCocktailsEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecentCocktails(recentCocktailsEntity)
        }

    /** RETROFIT */
    var recentCocktailResponse: MutableLiveData<NetworkResult<Recent>> = MutableLiveData()

    fun getRecentCocktails() = viewModelScope.launch {
        getRecentCocktailsSafeCall()
    }

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
                recentCocktailResponse.value = NetworkResult.Error("Recent cocktails not found.")
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