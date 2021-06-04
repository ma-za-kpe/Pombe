package com.maku.pombe.models.TenRandom


import com.google.gson.annotations.SerializedName

data class TenRandom(
    @SerializedName("drinks")
    val drinks: List<Drink>
)