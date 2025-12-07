package Service;

public class ServiceJdbcException extends RuntimeException {
    /**
     * implementamos un constructor que invoca al constructor padre
     * y recibe un parametro de tipo String llamado mensaje
     */
    public ServiceJdbcException(String message) {
        //llamamos al constructor padre para mostrar el mensaje
        super(message);
    }
    /**
     * implementamos un constructor que recibe dos parametros
     * de tipo String message y tambien la causa del error
     */
    public ServiceJdbcException(String message, Throwable cause) {
        super(message, cause);
    }
}
