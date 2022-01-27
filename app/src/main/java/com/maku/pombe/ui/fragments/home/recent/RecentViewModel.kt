package com.maku.pombe.ui.fragments.home.recent

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.maku.core.data.repo.CocktailRepository
import com.maku.core.usecases.GetRecentCocktails
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecentViewModel @Inject constructor(
    private val getRecentCocktails: GetRecentCocktails,
    application: Application
) : AndroidViewModel(application) {

    fun observeRecentLocalCocktails() = getRecentCocktails.readRecentCocktails
    suspend fun recentCocktails() = getRecentCocktails.getRecentCocktails()

}