package com.example.littlelemon.data.remote

import com.example.littlelemon.data.remote.model.MenuItemNetwork
import com.example.littlelemon.data.remote.model.MenuNetworkResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class MenuApi(
    private val httpClient: HttpClient,
) {
    private val json =
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

    suspend fun fetchMenu(): List<MenuItemNetwork> {
        // GitHub raw endpoints may return JSON with a non-application/json Content-Type (e.g., text/plain),
        // so we decode manually instead of relying on ContentNegotiation.
        val rawJson = httpClient.get(MENU_URL).bodyAsText()
        val response: MenuNetworkResponse = json.decodeFromString(rawJson)
        return response.menu
    }

    companion object {
        const val MENU_URL: String =
            "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
    }
}
