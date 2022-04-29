package com.maku.pombe.common.data.api.model.popular


import com.google.gson.annotations.SerializedName

data class PopularResponse(
    @SerializedName("drinks")
    val drinks: List<DrinkPopular>
)