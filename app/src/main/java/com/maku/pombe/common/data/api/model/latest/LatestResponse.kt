package com.maku.pombe.common.data.api.model.latest


import com.google.gson.annotations.SerializedName

data class LatestResponse(
    @SerializedName("drinks")
    val drinks: List<DrinkLatest>
)