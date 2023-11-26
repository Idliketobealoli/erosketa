package ies.joatzel.categoriesspring.models

import java.util.UUID

/**
 * Modelo de Categoría que será guardado en la "base de datos" [aunque de momento la BBDD sea un mapa]
 * @author Daniel Rodríguez Muñoz
 */
data class Category(
    val id: Long,
    val uuid: UUID = UUID.randomUUID(),
    val name: String,
    val description: String,
    val color: String? = "000000"
)