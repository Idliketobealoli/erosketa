package ies.joatzel.categoriesspring.repositories

/**
 * Interfaz que determina qué funciones deberán implementar los repositorios de nuestra API.
 * @author Daniel Rodríguez Muñoz
 */
interface CRUDRepository<T, ID> {
    fun findById(id: ID): T?
    fun existsById(id: ID): Boolean
    fun save(t: T): T
    fun deleteById(id: ID): Boolean
    fun delete(t: T): Boolean
    fun count(): Int
    fun deleteAll()
}