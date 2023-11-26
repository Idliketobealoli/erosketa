package ies.joatzel.categoriesspring.db

import ies.joatzel.categoriesspring.models.Category
import ies.joatzel.categoriesspring.models.Product
import java.time.LocalDateTime
import java.util.*

/**
 * Datos iniciales de categoría.
 * @author Daniel Rodríguez Muñoz
 */
fun categories() = mutableMapOf(
    Pair(1L, Category(
        id = 1L,
        uuid = UUID.fromString("00000000-0000-0000-0000-000000000000"),
        name = "Categoria 1",
        description = "Descripcion generica 1",
        color = "purple"
    )),
    Pair(2L, Category(
        id = 2L,
        name = "Categoria 2",
        description = "Descripcion generica 2",
        color = "ff00ff"
    )),
    Pair(3L, Category(
        id = 3L,
        name = "Categoria 3",
        description = "Descripcion generica 3",
        color = "00ff00"
    ))
)

fun products() = mutableMapOf(
    Pair(1L, Product(
        id = 1L,
        uuid = UUID.fromString("00000000-0000-0000-0000-000000000001"),
        name = "Producto 1",
        description = "Descripcion generica 1",
        price = 123.45,
        stock = 10,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
        categoryId = 1L
    )),
    Pair(2L, Product(
        id = 2L,
        name = "Producto 2",
        description = "Descripcion generica 2",
        price = 123.45,
        stock = 10,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
        categoryId = 1L
    )),
    Pair(3L, Product(
        id = 3L,
        name = "Producto 3",
        description = "Descripcion generica 3",
        price = 123.45,
        stock = 10,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
        categoryId = 2L
    )),
    Pair(4L, Product(
        id = 4L,
        name = "Producto 4",
        description = "Descripcion generica 4",
        price = 123.45,
        stock = 10,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
        categoryId = 3L
    ))
)