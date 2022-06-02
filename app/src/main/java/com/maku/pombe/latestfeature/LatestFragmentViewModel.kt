package com.maku.pombe.latestfeature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maku.logging.Logger
import com.maku.pombe.common.domain.model.NetworkException
import com.maku.pombe.common.domain.model.NetworkUnavailableException
import com.maku.pombe.common.domain.model.NoDrinksException
import com.maku.pombe.common.domain.model.latest.LatestDrink
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LatestFragmentViewModel @Inject constructor(
    private val getLatestDrinks: GetLatestDrinks,
    private val requestLatestDrinksList: RequestLatestDrinksList,
    private val uiLatestDrinkMapper: UiLatestDrinkMapper,
    private val compositeDisposable: CompositeDisposable
): ViewModel() {

    val state: LiveData<LatestDrinkViewState> get() = _state
    private val _state = MutableLiveData<LatestDrinkViewState>()
    init {
        _state.value = LatestDrinkViewState()
        subscribeToPombeDbUpdates()
    }

    private fun subscribeToPombeDbUpdates() {
        getLatestDrinks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onNewPombeList(it) },
                { onFailure(it) }
            )
            .addTo(compositeDisposable)
    }

    private fun onNewPombeList(drink: List<LatestDrink>) {
        _state.value = state.value!!.copy( loading = true)
        Logger.d("vm onNewPombeList: ${drink.size}")

            val latestDrinks = drink.map { uiLatestDrinkMapper.mapToView(it) }
            // TODO: add updates at value while inserting into room db
            // This ensures that new items are added below the already existing ones, thus avoiding
            // repositioning of items that are already visible, as it can provide for a confusing UX. A
            // nice alternative to this would be to add an "updatedAt" field to the Room entities, so
            // that we could actually order them by something that we completely control.
            val currentList = state.value!!.drinks
            val newLatest = latestDrinks.subtract(currentList)
            val updatedList = currentList + newLatest

            _state.value = state.value!!.copy( loading = false, drinks = updatedList)
    }

    fun onEvent(event: LatestDrinkEvent) {
        when(event) {
            is LatestDrinkEvent.RequestLatestDrinksList ->
                loadLatestDrinks()
        }
    }

    private fun loadLatestDrinks() {
        if (state.value!!.drinks.isEmpty()) {
            loadDrinks()
        }
    }

    private fun loadDrinks() {
        Logger.d("vm loadDrinks:")

        _state.value = state.value!!.copy( loading = true)
        val errorMessage = "Failed to fetch pombes"
        val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage){
            onFailure(it)
        }
        viewModelScope.launch(exceptionHandler) {
            delay(3000)
            requestLatestDrinksList()
            // request drinks!
            _state.value = state.value!!.copy( loading = false)
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
                    failure = Event(failure)
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}