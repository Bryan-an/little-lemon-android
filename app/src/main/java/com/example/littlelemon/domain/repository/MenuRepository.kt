package com.example.littlelemon.domain.repository

import com.example.littlelemon.domain.model.MenuItem
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun observeMenuItems(): Flow<List<MenuItem>>

    suspend fun syncMenu(): Result<Unit>
}
