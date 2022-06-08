package com.maku.pombe.common.data.api

import com.maku.pombe.BuildConfig

object ApiConstants {
  const val BASE_ENDPOINT = "https://www.thecocktaildb.com/"
}

object ApiParameters{
  @JvmField
  val KEY = BuildConfig.API_KEY
}
