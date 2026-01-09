package com.example.littlelemon.data.repository

import com.example.littlelemon.data.local.db.MenuItemDao
import com.example.littlelemon.data.mapper.toDomain
import com.example.littlelemon.data.mapper.toEntity
import com.example.littlelemon.data.remote.MenuApi
import com.example.littlelemon.domain.model.MenuItem
import com.example.littlelemon.domain.repository.MenuRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DefaultMenuRepository(
    private val menuApi: MenuApi,
    private val menuItemDao: MenuItemDao,
) : MenuRepository {
    override fun observeMenuItems(): Flow<List<MenuItem>> = menuItemDao.observeMenuItems().map { entities ->
        entities.map { it.toDomain() }
    }

    override suspend fun syncMenu(): Result<Unit> = runCatching {
        val entities =
            withContext(Dispatchers.IO) {
                val networkItems = menuApi.fetchMenu()
                networkItems.map { it.toEntity() }
            }

        withContext(Dispatchers.IO) {
            menuItemDao.replaceAll(entities)
        }

        Unit
    }
}
