package com.example.littlelemon.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class MenuNetworkResponse(
    val menu: List<MenuItemNetwork>,
)

@Serializable
data class MenuItemNetwork(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String,
)
