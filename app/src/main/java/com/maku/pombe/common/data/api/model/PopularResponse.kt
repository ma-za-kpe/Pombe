package com.maku.pombe.common.data.api.model


import com.google.gson.annotations.SerializedName

data class PopularResponse(
    @SerializedName("drinks")
    val drinks: List<Drink>
)