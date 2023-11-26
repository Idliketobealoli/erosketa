package ies.joatzel.categoriesspring.controllers

import ies.joatzel.categoriesspring.config.APIConfig
import ies.joatzel.categoriesspring.dto.CategoryDTO
import ies.joatzel.categoriesspring.dto.CategoryDTOcreate
import ies.joatzel.categoriesspring.services.CategoryService
import ies.joatzel.categoriesspring.validators.categoryIsValid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.lang.Nullable
import org.springframework.web.bind.annotation.*
import java.util.UUID

/**
 * Controlador de las categorías. Se encargará de asignarle una ruta a cada método que queramos
 * usar para nuestra api, y convertir los resultados de los servicios en respuestas HTTP.
 * @author Daniel Rodríguez Muñoz
 */
@RestController
@RequestMapping("${APIConfig.API_PATH}/categories")
class CategoryController @Autowired constructor(
    private val service: CategoryService
) {

    /**
     * Find all de las categorías.
     * Si hay parámetro name, filtra por las categorías que contengan esa cadena.
     * @param name Nombre por el cual filtrar la búsqueda (opcional)
     * @return ResponseEntity [200] - La lista de categorías, o una lista vacía.
     */
    @GetMapping("")
    fun findAllCategories(@RequestParam @Nullable name: String?): ResponseEntity<List<CategoryDTO>> {
        return ResponseEntity.ok(service.findAll(name))
    }

    /**
     * Find categoría por ID.
     * Busca una categoría en base a un ID dado. Si la encuentra, la devuelve.
     * Si no, devuelve una respuesta de NotFound.
     * @param id ID de la categoría que queremos buscar.
     * @return ResponseEntity [200] - La categoría, si la encuentra.
     *
     * ResponseEntity [404] - Si no encuentra la categoría.
     */
    @GetMapping("/{id}")
    fun findCategoryById(@PathVariable id: Long): ResponseEntity<CategoryDTO> {
        val category = service.findById(id)

        return if (category != null) {
            ResponseEntity.ok(category)
        }
        else {
            ResponseEntity.notFound().build()
        }
    }

    /**
     * Find categoría por UUID.
     * Busca una categoría en base a un UUID dado. Si la encuentra, la devuelve.
     * Si no, devuelve una respuesta de NotFound.
     * @param uuid UUID de la categoría que queremos buscar.
     * @return ResponseEntity [200] - La categoría, si la encuentra.
     *
     * ResponseEntity [404] - Si no encuentra la categoría.
     */
    @GetMapping("/uuid/{uuid}")
    fun findCategoryByUuid(@PathVariable uuid: UUID): ResponseEntity<CategoryDTO?> {
        val category = service.findByUuid(uuid)

        return if (category != null) {
            ResponseEntity.ok(category)
        }
        else {
            ResponseEntity.notFound().build()
        }
    }

    /**
     * POST de categoría.
     * guarda o modifica una categoría en la base de datos.
     * @param category Datos de la categoría a insertar / modificar.
     * @return ResponseEntity [200] - La categoría, si la pudo insertar / modificar.
     *
     * ResponseEntity [400] - Si el body es inválido.
     */
    @PostMapping
    fun postCategory(@RequestBody category: CategoryDTOcreate): ResponseEntity<CategoryDTO> {
        if (!categoryIsValid(category)) { return ResponseEntity.badRequest().build() }

        val saved = service.save(category)

        return ResponseEntity.ok(saved)
    }

    /**
     * DELETE de categoría.
     * Busca una categoría en base a un ID dado. Si la encuentra, la borra y devuelve true.
     * Si no, devuelve false.
     * @param id ID de la categoría que queremos borrar.
     * @return ResponseEntity [200] - True si la borró, false si no la borró.
     */
    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable id: Long): ResponseEntity<Boolean> {
        return ResponseEntity.ok(service.deleteById(id))
    }
}