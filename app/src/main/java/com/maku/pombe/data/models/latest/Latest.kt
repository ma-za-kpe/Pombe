package com.maku.pombe.data.models.latest


import com.google.gson.annotations.SerializedName

data class Latest(
    @SerializedName("drinks")
    val drinks: List<Drink>
)