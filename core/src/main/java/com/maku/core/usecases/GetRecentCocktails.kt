package com.maku.core.usecases

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.maku.core.data.local.entities.RecentCocktailsEntity
import com.maku.core.data.repo.CocktailRepository
import com.maku.core.domain.recent.Recent
import com.maku.core.utils.NetworkResult
import retrofit2.Response
import javax.inject.Inject

class GetRecentCocktails @Inject constructor(private val repository: CocktailRepository, private val application: Application) {
    val readRecentCocktails: LiveData<List<RecentCocktailsEntity>> = repository.local.readRecentCocktails().asLiveData()

    private suspend fun insertRecentCocktails(recentCocktailsEntity: RecentCocktailsEntity) =
            repository.local.insertRecentCocktails(recentCocktailsEntity)


    var recentCocktailResponse: MutableLiveData<NetworkResult<Recent>> = MutableLiveData()

    suspend fun getRecentCocktails() = getRecentCocktailsSafeCall()

    // recent
    suspend fun getRecentCocktailsSafeCall() {
        recentCocktailResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getRecentCocktails()
                // TimberLog.d("response$response")
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

    private suspend fun offlineCacheRecentCocktails(recent: Recent) {
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
        val connectivityManager = application.getSystemService(
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