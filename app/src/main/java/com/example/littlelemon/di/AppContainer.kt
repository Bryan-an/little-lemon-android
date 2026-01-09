package com.example.littlelemon.di

import android.content.Context
import androidx.room.Room
import com.example.littlelemon.data.local.db.LittleLemonDatabase
import com.example.littlelemon.data.remote.KtorClientFactory
import com.example.littlelemon.data.remote.MenuApi
import com.example.littlelemon.data.repository.DefaultMenuRepository
import com.example.littlelemon.domain.repository.MenuRepository

class AppContainer private constructor(
    context: Context,
) {
    private val appContext = context.applicationContext

    private val database: LittleLemonDatabase =
        Room
            .databaseBuilder(
                appContext,
                LittleLemonDatabase::class.java,
                "little_lemon.db",
            ).build()

    private val httpClient = KtorClientFactory.create()
    private val menuApi = MenuApi(httpClient)

    val menuRepository: MenuRepository =
        DefaultMenuRepository(
            menuApi = menuApi,
            menuItemDao = database.menuItemDao(),
        )

    companion object {
        @Volatile private var instance: AppContainer? = null

        fun get(context: Context): AppContainer = instance
            ?: synchronized(this) {
                instance ?: AppContainer(context).also { instance = it }
            }
    }
}
