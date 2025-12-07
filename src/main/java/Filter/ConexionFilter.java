package Filter;
/*
 * Autor: Génesis Escobar
 * Fecha: 05-12-2025
 * Versión: 1.0
 * Descripción:
 * Filtro encargado de gestionar la conexión JDBC para toda la aplicación.
 * Intercepta cada solicitud entrante, crea una conexión, la comparte con los
 * servlets/recursos REST y controla explícitamente las transacciones mediante
 * commit y rollback. Garantiza integridad de datos y centraliza la apertura
 * y cierre de conexiones.
 */
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import Service.ServiceJdbcException;
import Util.Conexion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Aplica el filtro a todas las rutas del proyecto (/*).
 * Esto permite que cualquier servlet, JSP o recurso REST
 * pueda usar la conexión asignada en la solicitud.
 */
@WebFilter("/*")
public class ConexionFilter implements Filter {
    /**
     * una clase filter en java es un objeto que realiza tareas
     * de filtrado en las solicitudes cliente servidor
     * respuesta a un recurso: los filtros se pueden ejecutar en servidores compatibles con Jakarta EE
     * los filtros interceptan solicitudes y respuestas de manera dinamica para transformar o utilizar la informacion
     * que contienen. el filtrado se realiza mediante el metodo doFilter
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        /**
         * request: peticion que hace el cliente
         * response: respuesta del servidor
         * filterchain: es una clase de filtro que representa el flujo del procesamiento
         * este metodo llama al metodo chain.dofilter(request, response)
         * dentro de un filtro pasa la solicitud,
         * el siguiente paso la clase filtra o te devuelve el recurso destino
         * que puede ser un servlet o jsp
         */

        // Abrimos una conexión utilizando la clase utilitaria "Conexion"
        try (Connection connection = Conexion.getConnection()) {
            // Asegura que el manejo de transacciones sea manual
            if (connection.getAutoCommit()) {
                //cambiamos a una conexion manual
                connection.setAutoCommit(false);
            }
            try {
                //agregamos la conexion como un atributo en la solicitud
                //esto nos permite que otros componentes como servlet o DAOS
                //puedan acceder a la conexion
                request.setAttribute("conn", connection);
                //pasa la solicitud y la respuesta al siguiente filtro o al recurso destino
                chain.doFilter(request, response);
                /**
                 * si el procesamiento se realizo correctamente sin lanzar excepciones
                 * se confirma la solicitud y se aplica todos los cambios a la bdd
                 */
                connection.commit();
                /**
                 * si ocurre algun error durante el procesamiento (dentro del doFilter),
                 * se captura la excepcion
                 */
            }catch (SQLException | ServiceJdbcException e) {
                //se deshace los cambios con un rollback y de esa forma se
                // mantiene la integridad de los datos
                connection.rollback();
                //enviamos un codigo de error Http 500 al cliente
                //indicando un problema interno del servidor
                ((HttpServletResponse)response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                e.printStackTrace();


            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
