package ies.joatzel.categoriesspring.config

import org.springframework.context.annotation.Configuration

/**
 * Clase que contiene las constantes de la API, tales como la version o la ruta principal.
 * De esta forma, si cambiamos la ruta principal, se cambia en todos los controladores a la vez.
 * @author Daniel Rodríguez Muñoz
 */
@Configuration
class APIConfig {
    companion object {
        const val API_PATH = "/erosketa"
        // const val API_VERSION = "1.0"
    }
}