package Filter;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import Service.ServiceJdbcException;
import Util.Conexion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

//implementamos una anotacion. esta anotacion
// me sirve para poder utilizar la conexion en cualquier parte
//de mi aplicacion
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
        //obtenemos la conexion
        try (Connection connection = Conexion.getConnection()) {
            //verificamos que la conexion no se realice automaticamente
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
