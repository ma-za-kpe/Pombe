package com.maku.pombe.common.data.api.model.tenrandom


import com.google.gson.annotations.SerializedName

data class ApiTenRandom(
    @SerializedName("drinks")
    val drinks: List<DrinkTenRandom>
)