package ies.joatzel.categoriesspring.services

import ies.joatzel.categoriesspring.dto.CategoryDTO
import ies.joatzel.categoriesspring.dto.CategoryDTOcreate
import ies.joatzel.categoriesspring.mappers.fromDTO
import ies.joatzel.categoriesspring.mappers.toDTO
import ies.joatzel.categoriesspring.models.Category
import ies.joatzel.categoriesspring.repositories.CategoryRepository
import ies.joatzel.categoriesspring.repositories.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.*

/**
 * Servicio de categorías.
 *
 * Llamará al repositorio para realizar las operaciones necesarias de las categorías, y mapeará lo
 * que este devuelva a los DTOs correspondientes.
 *
 * También meterá los resultados de ciertas operaciones en una memoria caché, de tal forma que si se vuelve a
 * realizar la misma consulta varias veces, no se esté llamando a la BBDD todo el rato, mejorando por tanto el rendimiento.
 * @author Daniel Rodríguez Muñoz
 */
@Service
@CacheConfig(cacheNames = ["categories"])
class CategoryService @Autowired constructor(
    private val repo: CategoryRepository,
    private val productRepo: ProductRepository
) : ICategoryService {

    /**
     * Find All de categorías.
     * @return List<CategoryDTO> - Lista con todas las categorías de la BBDD mapeadas a sus respectivos DTOs.
     */
    override fun findAll(name: String?): List<CategoryDTO> {
        return repo.findAll(name).map { it.toDTO(productRepo) }
    }

    /**
     * Busca la categoría cuyo ID coincide con el pasado por parámetro.
     * @param id ID de la categoría a buscar.
     * @return CategoryDTO? - La categoría encontrada mapeada a DTO, o null si no la encuentra.
     */
    @Cacheable
    override fun findById(id: Long): CategoryDTO? {
        return repo.findById(id)?.toDTO(productRepo)
    }

    /**
     * Busca la categoría cuyo UUID coincide con el pasado por parámetro.
     * @param uuid UUID de la categoría a buscar.
     * @return CategoryDTO? - La categoría encontrada mapeada a DTO, o null si no la encuentra.
     */
    @Cacheable
    override fun findByUuid(uuid: UUID): CategoryDTO? {
        return repo.findByUuid(uuid)?.toDTO(productRepo)
    }

    /**
     * Inserta / modifica en la BBDD la categoría pasada por parámetro.
     * @param category CategoryDTOcreate que contiene la información a insertar / modificar.
     * @return CategoryDTO - La categoría una vez insertada / modificada, mapeada a DTO.
     */
    @CachePut
    override fun save(category: CategoryDTOcreate): CategoryDTO {
        return repo.save(category.fromDTO()).toDTO(productRepo)
    }

    /**
     * Borra la categoría cuyo ID coincide con el pasado por parámetro.
     * @param id ID de la categoría a borrar.
     * @return True - Si la categoría fue encontrada y borrada exitosamente.
     *
     * False - Si la categoría no pudo ser encontrada o borrada.
     */
    @CacheEvict
    override fun deleteById(id: Long): Boolean {
        productRepo.deleteAllByCategoryId(id)
        return repo.deleteById(id)
    }
}