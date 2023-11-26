package ies.joatzel.categoriesspring.repositories

import ies.joatzel.categoriesspring.db.categories
import ies.joatzel.categoriesspring.models.Category
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repositorio de Categoría.
 *
 * Encargado de realizar las operaciones CRUD relacionadas con las categorías, así como
 * operaciones especiales como la busqueda filtrada por nombre.
 * @author Daniel Rodríguez Muñoz
 */
@Repository
class CategoryRepository : ICategoryRepository {
    // El mapa de categorías. Cuando tengamos una BBDD real, esto desaparecerá.
    private val categories: MutableMap<Long, Category> = categories()

    /**
     * Busca todas las categorías que contengan la cadena de caracteres pasada por parámetro.
     *
     * De no haberse pasado una cadena por parámetro, busca todas las categorías.
     * @param name Cadena por la que filtraremos.
     * @return List<Category> - La lista de categorías.
     */
    override fun findAll(name: String?): List<Category> {
        return if (name != null) categories.values.filter { category ->
            category.name.lowercase().contains(name.lowercase())
        }
        else categories.values.toList()
    }

    /**
     * Busca la categoría cuyo UUID coincide con el pasado por parámetro.
     * @param uuid El UUID de la categoría que queremos buscar.
     * @return Category? - La categoría cuyo UUID coincide con el pasado por parámetro, o null si no lo encontró.
     */
    override fun findByUuid(uuid: UUID): Category? {
        return categories.values.firstOrNull {
            category -> category.uuid == uuid
        }
    }

    /**
     * Busca la categoría cuyo ID coincide con el pasado por parámetro.
     * @param id El ID de la categoría que queremos buscar.
     * @return Category? - La categoría cuyo ID coincide con el pasado por parámetro, o null si no lo encontró.
     */
    override fun findById(id: Long): Category? {
        return categories.getOrDefault(id, null)
    }

    // Esta función acabó no siendo utilizada.
    // Se quedará aquí en caso de que se necesite en el futuro.
    override fun existsById(id: Long): Boolean {
        return categories.containsKey(id)
    }

    /**
     * Guarda la categoría pasada por parámetro, o la modifica si ya existía en la BBDD.
     * @param t Categoría a insertar / modificar.
     * @return Category - La categoría una vez insertada o modificada.
     */
    override fun save(t: Category): Category {
        return if (categories.containsKey(t.id)) { update(t) }
        else create(t)
    }

    /**
     * Actualiza una categoría existente con los campos de la categoría pasada por parámetro.
     * @param t Categoría con la información a actualizar.
     * @return Category - La categoría actualizada.
     */
    private fun update(t: Category): Category {
        val category = categories[t.id]

        val updatedCategory = Category(
            category!!.id,
            category.uuid,
            t.name,
            t.description,
            t.color
        )

        categories[updatedCategory.id] = updatedCategory
        return updatedCategory
    }

    /**
     * Crea una categoría nueva en base a la categoría pasada por parámetro.
     * @param t Categoría con la información a insertar.
     * @return Category - La categoría creada.
     */
    private fun create(t: Category): Category {
        var lastId = categories.keys.max() ?: 0L

        lastId++
        val newCategory = Category(
            lastId,
            UUID.randomUUID(),
            t.name,
            t.description,
            t.color
        )

        categories[lastId] = newCategory
        return newCategory
    }

    /**
     * Borra una categoría existente en base a un ID.
     * @param id ID de la categoría a borrar.
     * @return True - Si la categoría existía y se pudo borrar.
     *
     * False - Si la categoría no existía o no se pudo borrar.
     */
    override fun deleteById(id: Long): Boolean {
        return (categories.remove(id) != null)
    }

    // Esta función acabó no siendo utilizada.
    // Se quedará aquí en caso de que se necesite en el futuro.
    override fun delete(t: Category): Boolean {
        return (categories.remove(t.id) != null)
    }

    // Esta función acabó no siendo utilizada.
    // Se quedará aquí en caso de que se necesite en el futuro.
    override fun count(): Int {
        return categories.size
    }

    // Esta función acabó no siendo utilizada.
    // Se quedará aquí en caso de que se necesite en el futuro.
    override fun deleteAll() {
        return categories.clear()
    }
}