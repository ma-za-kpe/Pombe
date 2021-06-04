package com.maku.pombe.models.popular


import com.google.gson.annotations.SerializedName

data class Popular(
    @SerializedName("drinks")
    val drinks: List<Drink>
)