package com.example.littlelemon.data.remote

import com.example.littlelemon.data.remote.model.MenuItemNetwork
import com.example.littlelemon.data.remote.model.MenuNetworkResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class MenuApi(
    private val httpClient: HttpClient,
) {
    suspend fun fetchMenu(): List<MenuItemNetwork> {
        // ContentNegotiation is configured to treat both application/json and text/plain as JSON.
        val response: MenuNetworkResponse = httpClient.get(MENU_URL).body()
        return response.menu
    }

    companion object {
        const val MENU_URL: String =
            "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
    }
}
