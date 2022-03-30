package com.maku.pombe.common.data.api

import com.maku.pombe.BuildConfig

object ApiConstants {
  // https://www.thecocktaildb.com/api/json/v2/xxxx/popular.php
  const val BASE_ENDPOINT = "https://www.thecocktaildb.com/"
}

object ApiParameters{
  const val KEY = BuildConfig.API_KEY
}
