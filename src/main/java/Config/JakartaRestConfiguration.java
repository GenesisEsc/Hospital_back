package Config;
/*
 * Autor: Génesis Escobar
 * Fecha: 06-12-2025
 * Versión: 1.0
 * Descripción:
 * Clase de configuración principal para habilitar los servicios REST
 * dentro de la aplicación Jakarta EE.
 * Define el punto base (contexto) desde el cual se expondrán todos
 * los recursos y controladores REST implementados en el proyecto.
 */
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
/**
 * Establece la ruta base "/api" para todos los endpoints REST.
 * Gracias a esta configuración, cualquier recurso anotado con @Path
 * será accesible desde este contexto, permitiendo una organización
 * clara y centralizada de las URLs.
 */
@ApplicationPath("/api")
public class JakartaRestConfiguration extends Application {
    // No requiere métodos adicionales; Jakarta EE detecta automáticamente
    // los recursos REST presentes en el proyecto.
}