package com.example.littlelemon.data.mapper

import com.example.littlelemon.data.local.db.MenuItemEntity
import com.example.littlelemon.data.remote.model.MenuItemNetwork
import com.example.littlelemon.domain.model.MenuItem

fun MenuItemNetwork.toEntity(): MenuItemEntity = MenuItemEntity(
    id = id,
    title = title,
    description = description,
    price = price,
    image = image,
    category = category,
)

fun MenuItemEntity.toDomain(): MenuItem = MenuItem(
    id = id,
    title = title,
    description = description,
    price = price,
    image = image,
    category = category,
)
