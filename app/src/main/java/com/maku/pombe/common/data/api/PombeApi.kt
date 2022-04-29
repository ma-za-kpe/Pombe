package com.maku.pombe.common.data.api

import com.maku.pombe.common.data.api.model.latest.LatestResponse
import com.maku.pombe.common.data.api.model.popular.PopularResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PombeApi {
    // get popular cocktails
    @GET("api/json/v2/{key}/popular.php")
    suspend fun getPopularDrinks(
        @Path("key") key: Int,
    ): PopularResponse

    // get latest cocktails
    @GET("api/json/v2/{key}/latest.php")
    suspend fun getLatestDrinks(
        @Path("key") key: Int,
    ): LatestResponse
}
