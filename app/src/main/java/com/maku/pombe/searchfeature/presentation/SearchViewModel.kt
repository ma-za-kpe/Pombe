package com.maku.pombe.searchfeature.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maku.pombe.common.domain.model.latest.LatestDrink
import com.maku.pombe.common.presentation.model.mappers.UiLatestDrinkMapper
import com.maku.pombe.searchfeature.domain.models.SearchResults
import com.maku.pombe.searchfeature.domain.usecases.SearchCocktails
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchCocktails: SearchCocktails,
    private val uiLatestDrinkMapper: UiLatestDrinkMapper,
    private val compositeDisposable: CompositeDisposable
): ViewModel() {

    val state: LiveData<SearchViewState> get() = _state
    private val _state: MutableLiveData<SearchViewState> = MutableLiveData()

    // Behavior subjects start with an initial value.
    // A subject acts as both an observable and an observer
    private val querySubject = BehaviorSubject.create<String>()

    init {
        _state.value = SearchViewState()
    }

    fun onEvent(event: SearchEvent) {
        when(event) {
            is SearchEvent.PrepareForSearch -> prepareForSearch() // tell the vm that ui is ready to start searching
            else -> onSearchParametersUpdate(event)
        }
    }

    fun updateSearchWidgetState(newValue: String) {
        _state.value = state.value!!.copy( topAppBarSearch = newValue)
    }


    fun updateSearchTextState(newValue: String) {
        _state.value = state.value!!.copy( searchWidgetText = newValue)
    }

    private fun onSearchParametersUpdate(event: SearchEvent) {
        when (event) {
            is SearchEvent.QueryInput -> updateQuery(event.input)
            SearchEvent.PrepareForSearch -> TODO()
            else -> {}
        }
    }

    private fun updateQuery(input: String) {
        querySubject.onNext(input)
        if (input.isEmpty()) {
            setNoSearchQueryState()
        } else {
            setSearchingState()
        }
    }

    private fun setNoSearchQueryState() {
        _state.value = state.value!!.updateToNoSearchQuery()
    }

    private fun setSearchingState() {
        _state.value = state.value!!.updateToSearching()
    }

    private fun prepareForSearch() {
          setupSearchSubscription()
    }

    private fun setupSearchSubscription() {
        searchCocktails(querySubject)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onSearchResults(it) },
                { onFailure(it) }
            )
            .addTo(compositeDisposable)
    }

    private fun onSearchResults(searchResults: SearchResults) {
        val (drinks, searchParameters) = searchResults // cool destructing happening here
        if (drinks.isEmpty()) {
            // remote search section, unfortunately, feature not available
            // we could use this for a remote search
        } else {
            onSearchCokctailList(drinks)
        }
    }

    private fun onSearchCokctailList(drinks: List<LatestDrink>) {
        _state.value =
            state.value!!.updateToHasSearchResults(drinks.map { uiLatestDrinkMapper.mapToView(it) })
    }

    private fun onFailure(throwable: Throwable) {
        _state.value = state.value!!.updateToHasFailure(throwable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}