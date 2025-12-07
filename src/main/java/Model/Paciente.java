package Model;
/*
 * Autor: Génesis Escobar
 * Fecha: 05-12-2025
 * Versión: 1.0
 * Descripción:
 * Entidad que representa a un Paciente dentro del sistema hospitalario.
 * Contiene los datos personales y el estado del paciente. Implementa
 * Serializable para permitir su conversión entre objetos y flujos de datos,
 * especialmente útil en entornos Jakarta EE y en procesos de transporte
 * (JSON, sesiones, etc.).
 */
import java.io.Serializable;

public class Paciente implements Serializable {

    // Atributos privados: garantizan encapsulamiento y control de acceso
    private int id;
    private String nombre;
    private String cedula;
    private String correo;
    private int edad;
    private String direccion;
    private boolean activo;

    /**
     * Constructor vacío.
     * Obligatorio para que Jakarta EE pueda instanciar la clase automáticamente
     * al convertir JSON a Objetoy viceversa (deserialización/serialización).
     */
    public Paciente() {

    }

    /**
     * Constructor completo.
     * Facilita la creación rápida de objetos cuando ya se tienen todos los datos.
     */
    public Paciente(int id, String nombre, String cedula, String correo, int edad, String direccion, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.correo = correo;
        this.edad = edad;
        this.direccion = direccion;
        this.activo = activo;
    }

    // Getters y Setters: permiten leer y modificar los atributos de forma controlada.

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
