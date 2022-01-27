package com.maku.core.domain.recent


import com.google.gson.annotations.SerializedName

data class Recent(
    @SerializedName("drinks")
    val drinks: List<Drink>
)