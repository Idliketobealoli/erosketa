package ies.joatzel.categoriesspring.repositories

import ies.joatzel.categoriesspring.db.products
import ies.joatzel.categoriesspring.models.Product
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

/**
 * Repositorio de Producto.
 *
 * Encargado de realizar las operaciones CRUD relacionadas con las productos, así como
 * operaciones especiales como la busqueda filtrada por nombre.
 * @author Daniel Rodríguez Muñoz
 */
@Repository
class ProductRepository : IProductRepository {
    // El mapa de productos. Cuando tengamos una BBDD real, esto desaparecerá.
    private val products: MutableMap<Long, Product> = products()

    /**
     * Busca todas las productos que cumplan las condiciones pasadas por parámetro.
     *
     * Filtra por nombre y por ID de la categoria a la que pertenecen.
     *
     * De no haber ningun parámetro, devuelve todos los productos sin filtrar.
     * @param name Cadena por la que filtraremos.
     * @param categoryId ID de la categoria a la que pertenecen los productos que queremos buscar.
     * @return List<Product> - La lista de productos cuyo name contiene la cadena pasada por parámetro.
     */
    override fun findAll(name: String?, categoryId: Long?): List<Product> {
        var list = products.values.toList()
        if (name != null) {
            list = list.filter {
                category ->
                category.name.lowercase().contains(name.lowercase())
            }
        }
        if (categoryId != null) {
            list = list.filter {
                category ->
                category.categoryId == categoryId
            }
        }
        return list
    }

    /**
     * Busca el producto cuyo UUID coincide con el pasado por parámetro.
     * @param uuid El UUID de el producto que queremos buscar.
     * @return Product? - La producto cuyo UUID coincide con el pasado por parámetro, o null si no lo encontró.
     */
    override fun findByUuid(uuid: UUID): Product? {
        return products.values.firstOrNull {
                category -> category.uuid == uuid
        }
    }

    /**
     * Busca el producto cuyo ID coincide con el pasado por parámetro.
     * @param id El ID de el producto que queremos buscar.
     * @return Product? - La producto cuyo ID coincide con el pasado por parámetro, o null si no lo encontró.
     */
    override fun findById(id: Long): Product? {
        return products.getOrDefault(id, null)
    }

    // Esta función acabó no siendo utilizada.
    // Se quedará aquí en caso de que se necesite en el futuro.
    override fun existsById(id: Long): Boolean {
        return products.containsKey(id)
    }

    /**
     * Guarda el producto pasada por parámetro, o la modifica si ya existía en la BBDD.
     * @param t Producto a insertar / modificar.
     * @return Product - La producto una vez insertada o modificada.
     */
    override fun save(t: Product): Product {
        return if (products.containsKey(t.id)) { update(t) }
        else create(t)
    }

    /**
     * Actualiza una producto existente con los campos de el producto pasada por parámetro.
     * @param t Producto con la información a actualizar.
     * @return Product - La producto actualizada.
     */
    private fun update(t: Product): Product {
        val product = products[t.id]

        val updatedProduct = Product(
            product!!.id,
            product.uuid,
            t.name,
            t.description,
            t.price,
            t.stock,
            product.createdAt,
            LocalDateTime.now(),
            t.categoryId
        )

        products[updatedProduct.id] = updatedProduct
        return updatedProduct
    }

    override fun decreaseStock(id: Long): Product? {
        if (!products.containsKey(id)) { return null }
        val product = products[id]

        var newStock = product!!.stock -1
        if (newStock < 0) { newStock = 0 }
        val updatedProduct = Product(
            product.id,
            product.uuid,
            product.name,
            product.description,
            product.price,
            newStock,
            product.createdAt,
            LocalDateTime.now(),
            product.categoryId
        )

        products[updatedProduct.id] = updatedProduct
        return updatedProduct
    }

    /**
     * Crea una producto nueva en base a el producto pasada por parámetro.
     * @param t Producto con la información a insertar.
     * @return Product - La producto creada.
     */
    private fun create(t: Product): Product {
        var lastId = products.keys.max() ?: 0L

        lastId++
        val newProduct = Product(
            lastId,
            UUID.randomUUID(),
            t.name,
            t.description,
            t.price,
            t.stock,
            LocalDateTime.now(),
            LocalDateTime.now(),
            t.categoryId
        )

        products[lastId] = newProduct
        return newProduct
    }

    /**
     * Borra una producto existente en base a un ID.
     * @param id ID de el producto a borrar.
     * @return True - Si el producto existía y se pudo borrar.
     *
     * False - Si el producto no existía o no se pudo borrar.
     */
    override fun deleteById(id: Long): Boolean {
        return (products.remove(id) != null)
    }

    // Esta función acabó no siendo utilizada.
    // Se quedará aquí en caso de que se necesite en el futuro.
    override fun delete(t: Product): Boolean {
        return (products.remove(t.id) != null)
    }

    /**
     * Borra todos los productos cuyo categoryId corresponda con el pasado por parámetro.
     * @param categoryId ID de la categoría de la cual queremos borrar sus productos.
     */
    override fun deleteAllByCategoryId(categoryId: Long) {
        val productsToDelete = findAll(null, categoryId)

        productsToDelete.forEach { products.remove(it.id)}
    }

    // Esta función acabó no siendo utilizada.
    // Se quedará aquí en caso de que se necesite en el futuro.
    override fun count(): Int {
        return products.size
    }

    // Esta función acabó no siendo utilizada.
    // Se quedará aquí en caso de que se necesite en el futuro.
    override fun deleteAll() {
        return products.clear()
    }
}