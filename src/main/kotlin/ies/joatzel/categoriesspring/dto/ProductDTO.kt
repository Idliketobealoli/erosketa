package ies.joatzel.categoriesspring.dto

import ies.joatzel.categoriesspring.services.LocalDateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

/**
 * DTO de productos. Es el DTO que devuelve nuestra API.
 * @author Daniel Rodríguez Muñoz
 */
@Serializable
data class ProductDTO(
    val name: String,
    val description: String,
    val price: Double,
    val stock: Int = 0,
    @Serializable(with = LocalDateTimeSerializer::class) // ej: 2007-12-03T10:15:30
    @SerialName("created_at")
    val createdAt: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    @SerialName("updated_at")
    val updatedAt: LocalDateTime,
    val category: CategoryDTO?
)

/**
 * DTO de creación de productos.
 * Es el DTO que recibe nuestro POST con la información necesaria para crear o modificar una categoría.
 * @author Daniel Rodríguez Muñoz.
 */
@Serializable
data class ProductDTOcreate(
    val id: Long,
    val name: String,
    val description: String,
    val price: Double,
    val stock: Int = 0,
    val categoryId: Long
)
