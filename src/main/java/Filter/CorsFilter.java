package Filter;
/*
 * Autor: Génesis Escobar
 * Fecha: 05-12-2025
 * Versión: 1.0
 * Descripción:
 * Filtro CORS para habilitar el acceso a la API desde aplicaciones externas
 * (por ejemplo, Angular, React o Postman). Añade los encabezados necesarios
 * para permitir peticiones entre dominios (Cross-Origin Resource Sharing),
 * controlando qué métodos, orígenes y cabeceras están permitidos.
 */
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
/**
 * La anotación @Provider permite que Jakarta registre automáticamente
 * este filtro y lo aplique a todas las respuestas generadas por la API.
 */
@Provider
/**
 * Metodo que se ejecuta después de procesar la solicitud,
 * justo antes de enviar la respuesta al cliente.
 * Aquí se añaden encabezados CORS para permitir el acceso
 * desde otros dominios.
 */
public class CorsFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {

        // Permite que CUALQUIER origen acceda a la API
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");

        // Especifica qué cabeceras puede enviar el cliente en la solicitud
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");

        // Indica si se permiten credenciales (cookies, tokens).
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");

        // Define qué métodos HTTP están permitidos en solicitudes CORS
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
    }
}
