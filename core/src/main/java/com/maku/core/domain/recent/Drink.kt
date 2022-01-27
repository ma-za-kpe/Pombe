package com.maku.core.domain.recent


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Drink(
    @SerializedName("idDrink")
    val idDrink: String, // 178356
    @SerializedName("strAlcoholic")
    val strAlcoholic: String, // Alcoholic
    @SerializedName("strCategory")
    val strCategory: String, // Cocktail
    @SerializedName("strCreativeCommonsConfirmed")
    val strCreativeCommonsConfirmed: String, // No
    @SerializedName("strDrink")
    val strDrink: String, // Butterfly Effect
    @SerializedName("strDrinkThumb")
    val strDrinkThumb: String, // https://www.thecocktaildb.com/images/media/drink/ht3hnk1619704289.jpg
    @SerializedName("strGlass")
    val strGlass: String, // Hurricane glass
    @SerializedName("strImageSource")
    val strImageSource: String, // https://www.instagram.com/p/COQGZOXBOG2/
    @SerializedName("strIngredient1")
    val strIngredient1: String, // Raspberry Vodka
    @SerializedName("strIngredient2")
    val strIngredient2: String, // Cranberry Juice
    @SerializedName("strIngredient3")
    val strIngredient3: String, // Lemonade
    @SerializedName("strIngredient4")
    val strIngredient4: String, // Blue Curacao
    @SerializedName("strIngredient5")
    val strIngredient5: String, // Sugar Syrup
    @SerializedName("strIngredient6")
    val strIngredient6: String, // Lime Juice
    @SerializedName("strIngredient7")
    val strIngredient7: String, // Mint
    @SerializedName("strInstructions")
    val strInstructions: String, // Grab your boston tin, fill it with cubes ice and then simply chuck in all your ingredients apart from your lemonade.Now it’s time to shake what your mama gave you until all your ingredients are blended to perfection.Add some cubes of ice to your hurricane glass, give them a swill to cool the whole thing down and then strain your raspberry vodka cocktail of wonder into the glass.Top with lemonade and chuck a sprig of mint on top for garnish.You can either get drinking at this point or go and try and grab a few butterflies to finish, the choice really is yours.
    @SerializedName("strMeasure1")
    val strMeasure1: String, // 50 ml
    @SerializedName("strMeasure2")
    val strMeasure2: String, // 25 ml
    @SerializedName("strMeasure3")
    val strMeasure3: String, // 25 ml
    @SerializedName("strMeasure4")
    val strMeasure4: String, // 10 ml
    @SerializedName("strMeasure5")
    val strMeasure5: String, // 10 ml
    @SerializedName("strMeasure6")
    val strMeasure6: String, // Dash
    @SerializedName("strMeasure7")
    val strMeasure7: String, // Sprig

): Parcelable