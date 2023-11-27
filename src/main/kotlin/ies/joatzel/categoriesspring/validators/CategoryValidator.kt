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
fun CategoryDTOcreate.isValid(): Boolean {
    return !(id <= 0 ||
            name.trim().isBlank() ||
            description.trim().isBlank())
}