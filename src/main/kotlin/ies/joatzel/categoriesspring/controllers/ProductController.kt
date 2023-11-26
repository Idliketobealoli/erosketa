package ies.joatzel.categoriesspring.controllers

import ies.joatzel.categoriesspring.config.APIConfig
import ies.joatzel.categoriesspring.dto.ProductDTO
import ies.joatzel.categoriesspring.dto.ProductDTOcreate
import ies.joatzel.categoriesspring.repositories.CategoryRepository
import ies.joatzel.categoriesspring.services.ProductService
import ies.joatzel.categoriesspring.validators.productIsValid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.lang.Nullable
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * Controlador de los productos. Se encargará de asignarle una ruta a cada método que queramos
 * usar para nuestra api, y convertir los resultados de los servicios en respuestas HTTP.
 * @author Daniel Rodríguez Muñoz
 */
@RestController
@RequestMapping("${APIConfig.API_PATH}/products")
class ProductController @Autowired constructor(
    private val service: ProductService,
    private val categoryRepo: CategoryRepository
) {

    /**
     * Find all de los productos.
     * Si hay parámetro name, filtra por los productos que contengan esa cadena.
     * @param name Nombre por el cual filtrar la búsqueda (opcional)
     * @return ResponseEntity [200] - La lista de productos, o una lista vacía.
     */
    @GetMapping("")
    fun findAllProducts(@RequestParam @Nullable name: String?, @RequestParam @Nullable categoryId: Long?): ResponseEntity<List<ProductDTO>> {
        return ResponseEntity.ok(service.findAll(name, categoryId))
    }

    /**
     * Find producto por ID.
     * Busca una producto en base a un ID dado. Si la encuentra, la devuelve.
     * Si no, devuelve una respuesta de NotFound.
     * @param id ID de la producto que queremos buscar.
     * @return ResponseEntity [200] - El producto, si la encuentra.
     *
     * ResponseEntity [404] - Si no encuentra la producto.
     */
    @GetMapping("/{id}")
    fun findProductById(@PathVariable id: Long): ResponseEntity<ProductDTO> {
        val product = service.findById(id)

        return if (product != null) {
            ResponseEntity.ok(product)
        }
        else {
            ResponseEntity.notFound().build()
        }
    }

    /**
     * Find producto por UUID.
     * Busca una producto en base a un UUID dado. Si la encuentra, la devuelve.
     * Si no, devuelve una respuesta de NotFound.
     * @param uuid UUID de la producto que queremos buscar.
     * @return ResponseEntity [200] - El producto, si la encuentra.
     *
     * ResponseEntity [404] - Si no encuentra la producto.
     */
    @GetMapping("/uuid/{uuid}")
    fun findProductByUuid(@PathVariable uuid: UUID): ResponseEntity<ProductDTO?> {
        val product = service.findByUuid(uuid)

        return if (product != null) {
            ResponseEntity.ok(product)
        }
        else {
            ResponseEntity.notFound().build()
        }
    }

    /**
     * POST de producto.
     * guarda o modifica una producto en la base de datos.
     * @param product Datos de la producto a insertar / modificar.
     * @return ResponseEntity [200] - El producto, si la pudo insertar / modificar.
     *
     * ResponseEntity [400] - Si el body es inválido.
     */
    @PostMapping
    fun postProduct(@RequestBody product: ProductDTOcreate): ResponseEntity<ProductDTO> {
        if (!productIsValid(product, categoryRepo)) { return ResponseEntity.badRequest().build() }

        val saved = service.save(product)

        return ResponseEntity.ok(saved)
    }

    /**
     * PUT de producto.
     * Decrementa el stock de un producto de la base de datos.
     * @param id ID de la producto cuyo stock queremos decrementar.
     * @return ResponseEntity [200] - El producto, si lo pudo modificar.
     *
     * ResponseEntity [404] - Si no lo pudo encontrar.
     */
    @PutMapping("/{id}")
    fun decreaseStockProduct(@PathVariable id: Long): ResponseEntity<ProductDTO?> {
        val product = service.decreaseStock(id)

        return if (product != null) {
            ResponseEntity.ok(product)
        }
        else {
            ResponseEntity.notFound().build()
        }
    }

    /**
     * DELETE de producto.
     * Busca una producto en base a un ID dado. Si la encuentra, la borra y devuelve true.
     * Si no, devuelve false.
     * @param id ID de la producto que queremos borrar.
     * @return ResponseEntity [200] - True si la borró, false si no la borró.
     */
    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<Boolean> {
        return ResponseEntity.ok(service.deleteById(id))
    }
}