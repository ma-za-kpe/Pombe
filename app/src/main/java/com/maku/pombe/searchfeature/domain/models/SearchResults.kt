package com.maku.pombe.searchfeature.domain.models

import com.maku.pombe.common.domain.model.latest.LatestDrink

data class SearchResults(
    val drinks: List<LatestDrink>,
    val searchParameters: String
)