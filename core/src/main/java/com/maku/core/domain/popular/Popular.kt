package com.maku.core.domain.popular


import com.google.gson.annotations.SerializedName

data class Popular(
    @SerializedName("drinks")
    val drinks: List<Drink>
)