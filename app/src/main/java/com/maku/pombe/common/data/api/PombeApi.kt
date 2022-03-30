package com.maku.pombe.common.data.api

import com.maku.pombe.common.data.api.model.PopularResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PombeApi {
    // get popular cocktails
    @GET("api/json/v2/{key}/popular.php")
    suspend fun getNearbyAnimals(
        @Path("key") key: Int,
    ): PopularResponse
}
