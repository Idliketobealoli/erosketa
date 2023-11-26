package ies.joatzel.categoriesspring.repositories

import ies.joatzel.categoriesspring.models.Product
import java.util.*

interface IProductRepository : CRUDRepository<Product, Long> {
    fun findAll(name: String?, categoryId: Long?): List<Product>
    fun findByUuid(uuid: UUID): Product?
    fun decreaseStock(id: Long): Product?
    fun deleteAllByCategoryId(categoryId: Long)
}