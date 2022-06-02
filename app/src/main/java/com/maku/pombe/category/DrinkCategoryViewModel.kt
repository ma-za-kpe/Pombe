package com.maku.pombe.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maku.logging.Logger
import com.maku.pombe.category.usecases.GetDrinksCategory
import com.maku.pombe.common.domain.model.NetworkException
import com.maku.pombe.common.domain.model.NetworkUnavailableException
import com.maku.pombe.common.domain.model.NoDrinksException
import com.maku.pombe.common.domain.model.category.CategoryModel
import com.maku.pombe.common.presentation.Event
import com.maku.pombe.common.presentation.model.mappers.UiDrinkCategoryMapper
import com.maku.pombe.common.utils.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

@HiltViewModel
class DrinkCategoryViewModel @Inject constructor(
    private val getDrinksCategory: GetDrinksCategory,
    private val uiDrinkCategory: UiDrinkCategoryMapper,
    private val compositeDisposable: CompositeDisposable
): ViewModel() {

    val state: LiveData<DrinkCategoryViewState> get() = _state
    private val _state = MutableLiveData<DrinkCategoryViewState>()
    init {
        _state.value = DrinkCategoryViewState()
        subscribeToPombeDbUpdates()
    }

    fun setSelectedCategory(name: String, selected: Boolean){
        _state.value = state.value!!.setSelectedCategory(name, selected)
    }

    private fun subscribeToPombeDbUpdates() {
        getDrinksCategory()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onNewCategoryList(it) },
                { onFailure(it) }
            )
            .addTo(compositeDisposable)
    }

    private fun onNewCategoryList(it: List<CategoryModel>?) {
        val catDrinks = it?.map { uiDrinkCategory.mapToView(it) }
        val currentList = state.value!!.categories
        Logger.d("categories list $currentList")
        val newCats = catDrinks!!.subtract(currentList)
        val updatedList = currentList + newCats

        _state.value = state.value!!.copy(categories = updatedList)
    }

    private fun onFailure(failure: Throwable) {
        when (failure) {
            is NetworkException,
            is NetworkUnavailableException -> {
                _state.value = state.value!!.copy(
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
        compositeDisposable.clear() // 4
    }
}