package ies.joatzel.categoriesspring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Main de la aplicación.
 * @author Daniel Rodríguez Muñoz
 */

@SpringBootApplication
class CategoriesSpringApplication

fun main(args: Array<String>) {
    runApplication<CategoriesSpringApplication>(*args)
}
