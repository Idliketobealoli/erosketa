package ies.joatzel.categoriesspring.repositories

import ies.joatzel.categoriesspring.models.Category
import java.util.*

/**
 * Interfaz específica que implementará el repositorio de categorías.
 *
 * Contiene las funciones específicas de ctegoría.
 * @author Daniel Rodríguez Muñoz
 */
interface ICategoryRepository : CRUDRepository<Category, Long> {
    fun findAll(name: String?): List<Category>
    fun findByUuid(uuid: UUID): Category?
}