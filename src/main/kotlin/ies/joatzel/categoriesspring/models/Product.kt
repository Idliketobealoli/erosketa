package ies.joatzel.categoriesspring.models

import java.time.LocalDateTime
import java.util.*

data class Product(
    val id: Long,
    val uuid: UUID = UUID.randomUUID(),
    val name: String,
    val description: String,
    val price: Double,
    val stock: Int = 0,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val categoryId: Long
)
