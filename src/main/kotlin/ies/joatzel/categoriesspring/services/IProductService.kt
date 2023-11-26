package ies.joatzel.categoriesspring.services

import ies.joatzel.categoriesspring.dto.ProductDTO
import ies.joatzel.categoriesspring.dto.ProductDTOcreate
import java.util.*

interface IProductService {
    fun findAll(name: String?, categoryId: Long?): List<ProductDTO>
    fun findById(id: Long): ProductDTO?
    fun findByUuid(uuid: UUID): ProductDTO?
    fun save(category: ProductDTOcreate): ProductDTO
    fun decreaseStock(id: Long): ProductDTO?
    fun deleteById(id: Long): Boolean
}