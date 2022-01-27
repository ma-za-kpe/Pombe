package com.maku.core.domain.latest


import com.google.gson.annotations.SerializedName

data class Latest(
    @SerializedName("drinks")
    val drinks: List<Drink>
)