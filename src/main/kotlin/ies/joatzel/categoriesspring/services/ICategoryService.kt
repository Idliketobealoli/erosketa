package ies.joatzel.categoriesspring.services

import ies.joatzel.categoriesspring.dto.CategoryDTO
import ies.joatzel.categoriesspring.dto.CategoryDTOcreate
import java.util.*

interface ICategoryService {
    fun findAll(name: String?): List<CategoryDTO>
    fun findById(id: Long): CategoryDTO?
    fun findByUuid(uuid: UUID): CategoryDTO?
    fun save(category: CategoryDTOcreate): CategoryDTO
    fun deleteById(id: Long): Boolean
}