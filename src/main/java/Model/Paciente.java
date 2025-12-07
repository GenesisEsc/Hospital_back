package Model;

import java.io.Serializable;

public class Paciente implements Serializable {

    //atributos privados (encapsulamiento)
    private int id;
    private String nombre;
    private String cedula;
    private String correo;
    private int edad;
    private String direccion;
    private boolean activo;

    //1. Constructor Vacio (OBLIGATORIO)
    // Jakarta EE necesita esto para poder convertir el JSON a Objeto y viceversa.
    public Paciente() {

    }

    //2. Constructor con parametros, ayuda a crear objetod rápido
    public Paciente(int id, String nombre, String cedula, String correo, int edad, String direccion, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.correo = correo;
        this.edad = edad;
        this.direccion = direccion;
        this.activo = activo;
    }

    //Getters y Setters

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
