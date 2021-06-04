package com.maku.pombe.models.latest


import com.google.gson.annotations.SerializedName

data class Latest(
    @SerializedName("drinks")
    val drinks: List<Drink>
)