package Service;
/*
 * Autor: Génesis Escobar
 * Fecha: 06-12-2025
 * Versión: 1.0
 * Descripción:
 * Excepción personalizada para la capa Service en operaciones JDBC.
 * Permite manejar y propagar errores de manera más clara y controlada
 * dentro de la lógica de negocio, encapsulando fallos que ocurren
 * durante el acceso a la base de datos.
 */
public class ServiceJdbcException extends RuntimeException {

    /**
     * Constructor que recibe únicamente un mensaje descriptivo del error.
     * Este mensaje se envía al constructor de la clase padre
     * RuntimeException, permitiendo registrar qué falló
     * dentro de la operación de servicio.
     * @param message descripción del error ocurrido.
     */
    public ServiceJdbcException(String message) {
        //llamamos al constructor padre para mostrar el mensaje
        super(message);
    }
    /**
     * Constructor que recibe un mensaje y la causa original del error.
     * Este constructor permite encadenar excepciones (exception chaining),
     * preservando la pila de errores original para una buena trazabilidad.
     * Es útil cuando la excepción proviene de JDBC o de otra capa inferior.
     * @param message descripción del error.
     * @param cause excepción original que causó el fallo.
     */
    public ServiceJdbcException(String message, Throwable cause) {
        super(message, cause);// Pasamos mensaje y causa al constructor padre.
    }
}
