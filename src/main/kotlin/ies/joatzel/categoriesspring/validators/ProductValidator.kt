package ies.joatzel.categoriesspring.validators

import ies.joatzel.categoriesspring.dto.ProductDTOcreate
import ies.joatzel.categoriesspring.repositories.CategoryRepository

/**
 * Validador de ProductDTOcreate.
 *
 * Se asegura de que la información que contiene el DTO de creación sea válida:
 *  - Que el ID sea mayor a 0.
 *  - Que el nombre no sea una cadena vacía o todo espacios.
 *  - Que la descripción no sea una cadena vacía o todo espacios.
 *  - Que el precio sea mayor a 0.
 *  - Que el stock sea mayor a 0.
 *  - Que exista una categoria cuyo ID coincida con el campo categoryId.
 *
 *  @return True - Si cumple con las condiciones descritas anteriormente.
 *
 *  False - Si no cumple alguna de ellas.
 *  @author Daniel Rodriguez Muñoz
 */
fun productIsValid(product: ProductDTOcreate, categoryRepo: CategoryRepository): Boolean {
    val category = categoryRepo.findById(product.categoryId)

    return !(product.id <= 0 ||
            product.name.trim().isBlank() ||
            product.description.trim().isBlank() ||
            product.price <= 0 ||
            product.stock <= 0 ||
            category == null)
}