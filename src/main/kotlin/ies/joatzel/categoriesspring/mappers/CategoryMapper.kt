package ies.joatzel.categoriesspring.mappers

import ies.joatzel.categoriesspring.dto.CategoryDTO
import ies.joatzel.categoriesspring.dto.CategoryDTOcreate
import ies.joatzel.categoriesspring.models.Category
import ies.joatzel.categoriesspring.repositories.ProductRepository

/**
 * Función de extensión de Categoría para mapearla a CategoríaDTO.
 * @author Daniel Rodríguez Muñoz
 */
fun Category.toDTO(productRepo: ProductRepository) = CategoryDTO(
    name = name,
    description = description,
    color = color,
    productsAmount = productRepo.findAll(null, id).size
)

/**
 * Función de extensión de CategoríaDTOcreate para mapearla a Categoría.
 * @author Daniel Rodríguez Muñoz
 */
fun CategoryDTOcreate.fromDTO() = Category(
    id = id,
    name = name,
    description = description,
    color = color
)
