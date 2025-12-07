package Util;
/*
 * Autor: Génesis Escobar
 * Fecha: 06-12-2025
 * Versión: 1.0
 * Descripción:
 * Excepción personalizada utilizada dentro del proyecto para representar errores
 * propios de la lógica del sistema hospitalario. Extiende RuntimeException para
 * permitir que se lance sin necesidad de declararla explícitamente.
 *
 * Se utiliza principalmente en la capa Service para reportar fallos de validaciones,
 * reglas de negocio o problemas al interactuar con la capa de datos. Facilita la
 * lectura del código y permite brindar mensajes más claros hacia la API.
 */
public class HospitalException extends RuntimeException {
    /**
     * implementamos un constructor donde recibe como parametro un mensaje
     * luego llamamos a la clase constructor de la clase padre para que
     * lance la exception
     */
    public HospitalException(String message){
        super(message);
    }
    /**
     * implementamos un constructor donde recibe dos parámetros como el mensaje
     * y la causa de la excepcion. luego se invoca al constructor de la clase padre
     * donde devuelve la causa de forma tecnica
     */
    public HospitalException(String mensaje, Throwable cause) {
        super(cause);
    }
}

