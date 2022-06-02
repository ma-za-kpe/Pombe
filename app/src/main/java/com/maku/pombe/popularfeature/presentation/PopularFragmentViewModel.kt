package com.maku.pombe.popularfeature.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maku.logging.Logger
import com.maku.pombe.common.domain.model.NetworkException
import com.maku.pombe.common.domain.model.NetworkUnavailableException
import com.maku.pombe.common.domain.model.NoDrinksException
import com.maku.pombe.common.domain.model.popular.PopularDrink
import com.maku.pombe.common.presentation.Event
import com.maku.pombe.common.presentation.model.mappers.UiPopularDrinkMapper
import com.maku.pombe.common.utils.DispatchersProvider
import com.maku.pombe.common.utils.createExceptionHandler
import com.maku.pombe.popularfeature.domain.usecases.GetPopularDrinks
import com.maku.pombe.popularfeature.domain.usecases.RequestPopularDrinksList
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PopularFragmentViewModel @Inject constructor(
    private val getPopularDrinks: GetPopularDrinks,
    private val requestPopularDrinksList: RequestPopularDrinksList,
    private val uiPopularDrinkMapper: UiPopularDrinkMapper,
    private val dispatchersProvider: DispatchersProvider,
    private val compositeDisposable: CompositeDisposable
): ViewModel() {

    val state: LiveData<PopularDrinkViewState> get() = _state
    private val _state = MutableLiveData<PopularDrinkViewState>()
    init {
        _state.value = PopularDrinkViewState()
        subscribeToPopularDrinksUpdates()
    }
    // when view model has an event to trigger, it calls this public method
    fun onEvent(event: PopularDrinkEvent) {
        when (event) {
            is PopularDrinkEvent.RequestPopularDrinksList ->
                loadPopularDrinks()
        }
    }

    private fun subscribeToPopularDrinksUpdates() {
        getPopularDrinks()
            .observeOn(AndroidSchedulers.mainThread()) // 1
            .subscribe(
                { onNewDrinksList(it) },
                { onFailure(it) }
            )
            .addTo(compositeDisposable)
    }


    private fun onNewDrinksList(drink: List<PopularDrink>) {
        _state.value = state.value!!.copy( loading = true)
            val popularDrinks = drink.map { uiPopularDrinkMapper.mapToView(it) }
            val currentList = state.value!!.drinks
            val newPopular = popularDrinks.subtract(currentList)
            val updatedList = currentList + newPopular

            _state.value = state.value!!.copy( loading = false, drinks = updatedList)
    }

    private fun loadPopularDrinks() {
        // this if statement prevents multiple Api calls
        if (state.value!!.drinks.isEmpty()) {
            val errorMessage = "Failed to fetch popular drinks"
            val exceptionHandler =
                viewModelScope.createExceptionHandler(errorMessage)
                { onFailure(it) }
            viewModelScope.launch(exceptionHandler) {
                // request more drinks!
                withContext(dispatchersProvider.io()) {
                    Logger.d("Requesting drinks.")
                    requestPopularDrinksList()
                }
            }
        }
    }

    private fun onFailure(failure: Throwable) {
        when (failure) {
            is NetworkException,
            is NetworkUnavailableException -> {
                // here we are not changing the state, but simply replacing the it with an updated
                // copy of its self
                // the copy method belongs to a data class
                _state.value = state.value!!.copy(
                    loading = false,
                    failure = Event(failure) // Event to wrap Throwable so the UI reacts to it only
                    // once. Otherwise, everytime the app open, this error will be thrown
                )
            }
            is NoDrinksException -> {
                _state.value = state.value!!.copy(
                    failure = Event(failure)
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear() // 4
    }
}