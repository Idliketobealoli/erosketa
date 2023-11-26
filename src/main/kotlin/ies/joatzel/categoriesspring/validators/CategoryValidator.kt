package ies.joatzel.categoriesspring.validators

import ies.joatzel.categoriesspring.dto.CategoryDTOcreate

/**
 * Validador de CategoryDTOcreate.
 *
 * Se asegura de que la información que contiene el DTO de creación sea válida:
 *  - Que el ID sea mayor a 0.
 *  - Que el nombre no sea una cadena vacía o todo espacios.
 *  - Que la descripción no sea una cadena vacía o todo espacios.
 *
 *  @return True - Si cumple con las condiciones descritas anteriormente.
 *
 *  False - Si no cumple alguna de ellas.
 *  @author Daniel Rodriguez Muñoz
 */
fun categoryIsValid(category: CategoryDTOcreate): Boolean {
    return !(category.id <= 0 ||
            category.name.trim().isBlank() ||
            category.description.trim().isBlank())
}