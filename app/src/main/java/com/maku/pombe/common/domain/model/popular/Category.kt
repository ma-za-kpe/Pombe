package com.maku.pombe.common.domain.model.popular

enum class Category(private val displayName: String) {
    Ordinary_Drink("Ordinary Drink"),
    COCKTAIL("Cocktail"),
    SHAKE("Shake"),
    Other_Unknown("Other/Unknown"),
    COCOA("Cocoa"),
    NORTH_AMERICA("North America"),
    SHOT("Shot"),
    COFFEE_TEA("Coffee / Tea"),
    HOMEMADE_LIQUEUR("Homemade Liqueur"),
    PUNCH_PARTY_DRINK("Punch / Party Drink"),
    BEER("Beer"),
    SOFT_DRINK("Soft Drink");

    fun displayName(): String {
        return displayName
    }

    // Optionally and/or additionally, toString.
    override fun toString(): String {
        return displayName
    }
}
