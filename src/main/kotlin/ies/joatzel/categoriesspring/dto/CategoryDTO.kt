package ies.joatzel.categoriesspring.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO de categorías. Es el DTO que devuelve nuestra API.
 * @author Daniel Rodríguez Muñoz
 */
@Serializable
data class CategoryDTO(
    val name: String,
    val description: String,
    val color: String? = "000000",
    @SerialName("products_amount")
    val productsAmount: Int
)

/**
 * DTO de creación de categorías.
 * Es el DTO que recibe nuestro POST con la información necesaria para crear o modificar una categoría.
 * @author Daniel Rodríguez Muñoz.
 */
@Serializable
data class CategoryDTOcreate(
    val id: Long,
    val name: String,
    val description: String,
    val color: String? = "000000"
)
