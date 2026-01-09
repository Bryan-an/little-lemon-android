package com.example.littlelemon.feature.home

import androidx.annotation.StringRes
import com.example.littlelemon.R

enum class MenuCategory(
    @StringRes val labelRes: Int,
    val apiValue: String?,
) {
    All(labelRes = R.string.category_all, apiValue = null),
    Starters(labelRes = R.string.category_starters, apiValue = "starters"),
    Mains(labelRes = R.string.category_mains, apiValue = "mains"),
    Desserts(labelRes = R.string.category_desserts, apiValue = "desserts"),
    Drinks(labelRes = R.string.category_drinks, apiValue = "drinks"),
}
