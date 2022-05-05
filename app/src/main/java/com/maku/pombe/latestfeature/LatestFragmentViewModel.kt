package com.maku.pombe.latestfeature

import androidx.lifecycle.*
import com.maku.logging.Logger
import com.maku.pombe.common.domain.model.NetworkException
import com.maku.pombe.common.domain.model.NetworkUnavailableException
import com.maku.pombe.common.domain.model.NoDrinksException
import com.maku.pombe.common.presentation.Event
import com.maku.pombe.common.presentation.model.mappers.UiLatestDrinkMapper
import com.maku.pombe.common.utils.DispatchersProvider
import com.maku.pombe.common.utils.createExceptionHandler
import com.maku.pombe.latestfeature.domain.usecases.GetLatestDrinks
import com.maku.pombe.latestfeature.domain.usecases.RequestLatestDrinksList
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LatestFragmentViewModel @Inject constructor(
    private val getLatestDrinks: GetLatestDrinks,
    private val requestLatestDrinksList: RequestLatestDrinksList,
    private val uiLatestDrinkMapper: UiLatestDrinkMapper,
    private val dispatchersProvider: DispatchersProvider,
    private val compositeDisposable: CompositeDisposable
): ViewModel() {

    val state: LiveData<LatestDrinkViewState> get() = _state
    private val _state = MutableLiveData<LatestDrinkViewState>()
    init {
        _state.value = LatestDrinkViewState() // 3
    }
    // 4
    fun onEvent(event: LatestDrinkEvent) {
        when(event) {
            is LatestDrinkEvent.RequestLatestDrinksList ->
                loadLatestDrinks()
        }
    }

    private fun loadLatestDrinks() {
        Logger.d("Loading latest drinks.")
        if (state.value!!.drinks.isEmpty()) {
            Logger.d("Loading latest drinks. 2")
            loadDrinks()
        }
    }

    private fun loadDrinks() {
        Logger.d("Loading latest drinks. 3")
        val errorMessage = "Failed to fetch pombes"
        val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage){
            onFailure(it)
        }
        viewModelScope.launch(exceptionHandler) {
            Logger.d("Loading latest drinks. 4")
            // request drinks!
            withContext(dispatchersProvider.io()) {
                Logger.d("loading 5: Requesting latest drinks. ${requestLatestDrinksList()}")
                requestLatestDrinksList()
            }
        }
    }

    private fun onFailure(failure: Throwable) {
        when (failure) {
            is NetworkException,
            is NetworkUnavailableException -> {
                _state.value = state.value!!.copy(
                    loading = false,
                    failure = Event(failure)
                )
            }
            is NoDrinksException -> {
                _state.value = state.value!!.copy(
                    noMoreDrinks = true,
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