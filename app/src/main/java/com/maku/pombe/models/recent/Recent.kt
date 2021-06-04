package com.maku.pombe.models.recent


import com.google.gson.annotations.SerializedName

data class Recent(
    @SerializedName("drinks")
    val drinks: List<Drink>
)