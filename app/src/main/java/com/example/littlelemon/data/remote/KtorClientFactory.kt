package com.example.littlelemon.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorClientFactory {
    private val appJson =
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

    fun create(): HttpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            // Some endpoints may return JSON with a non-application/json Content-Type (e.g., text/plain).
            // Register JSON for both content types so response.body<T>() still works.
            json(appJson, contentType = ContentType.Application.Json)
            json(appJson, contentType = ContentType.Text.Plain)
        }
    }
}
