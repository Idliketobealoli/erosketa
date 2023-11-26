package ies.joatzel.categoriesspring.mappers

import ies.joatzel.categoriesspring.dto.ProductDTO
import ies.joatzel.categoriesspring.dto.ProductDTOcreate
import ies.joatzel.categoriesspring.models.Product
import ies.joatzel.categoriesspring.repositories.CategoryRepository
import ies.joatzel.categoriesspring.repositories.ProductRepository
import java.time.LocalDateTime

/**
 * Función de extensión de Producto para mapearla a ProductDTO.
 * @author Daniel Rodríguez Muñoz
 */
fun Product.toDTO(categoryRepo: CategoryRepository, productRepo: ProductRepository) = ProductDTO(
    name = name,
    description = description,
    price = price,
    stock = stock,
    createdAt = createdAt,
    updatedAt = updatedAt,
    category = categoryRepo.findById(categoryId)?.toDTO(productRepo)
)

/**
 * Función de extensión de ProductDTOcreate para mapearla a Producto.
 * @author Daniel Rodríguez Muñoz
 */
fun ProductDTOcreate.fromDTO() = Product(
    id = id,
    name = name,
    description = description,
    price = price,
    stock = stock,
    createdAt = LocalDateTime.now(),
    updatedAt = LocalDateTime.now(),
    categoryId = categoryId
)