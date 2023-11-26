package ies.joatzel.categoriesspring.services

import ies.joatzel.categoriesspring.dto.ProductDTO
import ies.joatzel.categoriesspring.dto.ProductDTOcreate
import ies.joatzel.categoriesspring.mappers.fromDTO
import ies.joatzel.categoriesspring.mappers.toDTO
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
 * Servicio de productos.
 *
 * Llamará al repositorio para realizar las operaciones necesarias de las productos, y mapeará lo
 * que este devuelva a los DTOs correspondientes.
 *
 * También meterá los resultados de ciertas operaciones en una memoria caché, de tal forma que si se vuelve a
 * realizar la misma consulta varias veces, no se esté llamando a la BBDD todo el rato, mejorando por tanto el rendimiento.
 * @author Daniel Rodríguez Muñoz
 */
@Service
@CacheConfig(cacheNames = ["products"])
class ProductService @Autowired constructor(
    private val repo: ProductRepository,
    private val categoryRepo: CategoryRepository
) : IProductService {

    /**
     * Find All de productos.
     * @return List<ProductDTO> - Lista con todas las productos de la BBDD mapeadas a sus respectivos DTOs.
     */
    override fun findAll(name: String?, categoryId: Long?): List<ProductDTO> {
        return repo.findAll(name, categoryId).map { it.toDTO(categoryRepo, repo) }
    }

    /**
     * Busca el producto cuyo ID coincide con el pasado por parámetro.
     * @param id ID de el producto a buscar.
     * @return ProductDTO? - El producto encontrada mapeada a DTO, o null si no la encuentra.
     */
    @Cacheable
    override fun findById(id: Long): ProductDTO? {
        return repo.findById(id)?.toDTO(categoryRepo, repo)
    }

    /**
     * Busca el producto cuyo UUID coincide con el pasado por parámetro.
     * @param uuid UUID de el producto a buscar.
     * @return ProductDTO? - El producto encontrada mapeada a DTO, o null si no la encuentra.
     */
    @Cacheable
    override fun findByUuid(uuid: UUID): ProductDTO? {
        return repo.findByUuid(uuid)?.toDTO(categoryRepo, repo)
    }

    /**
     * Inserta / modifica en la BBDD el producto pasada por parámetro.
     * @param category ProductDTOcreate que contiene la información a insertar / modificar.
     * @return ProductDTO - El producto una vez insertada / modificada, mapeada a DTO.
     */
    @CachePut
    override fun save(category: ProductDTOcreate): ProductDTO {
        return repo.save(category.fromDTO()).toDTO(categoryRepo, repo)
    }

    override fun decreaseStock(id: Long): ProductDTO? {
        return repo.decreaseStock(id)?.toDTO(categoryRepo, repo)
    }

    /**
     * Borra el producto cuyo ID coincide con el pasado por parámetro.
     * @param id ID de el producto a borrar.
     * @return True - Si el producto fue encontrada y borrada exitosamente.
     *
     * False - Si el producto no pudo ser encontrada o borrada.
     */
    @CacheEvict
    override fun deleteById(id: Long): Boolean {
        return repo.deleteById(id)
    }
}