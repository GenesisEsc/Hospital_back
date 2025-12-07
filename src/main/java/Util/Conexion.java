package Util;
/*
 * Autor: Génesis Escobar
 * Fecha: 06-12-2025
 * Versión: 1.0
 * Descripción:
 * Clase utilitaria encargada de gestionar la conexión JDBC hacia la base de datos.
 * Centraliza la configuración de acceso (URL, usuario y contraseña) y expone un
 * metodo estático para obtener conexiones activas, facilitando el uso desde filtros,
 * repositorios y servicios.
 *
 * Incluye, además, un metodo main opcional para realizar pruebas rápidas de
 * conectividad sin necesidad de desplegar toda la aplicación.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    // URL de conexión al servidor MySQL, incluyendo puerto y base de datos
    private static String url = "jdbc:mysql://localhost:3307/hospital_db?serverTimezone=UTC";
    // Credenciales de acceso
    private static String username = "root";
    private static String password = "root";

    /**
     * Metodo estático que devuelve una conexión activa hacia la base de datos.
     *Utiliza DriverManager para crear una nueva conexión cada vez que se
     * invoque. Se maneja a través de SQLException, permitiendo que las capas
     * superiores decidan cómo gestionar el error.
     *
     */
    public static Connection getConnection()throws SQLException {
        return DriverManager.getConnection(url,username,password);
    }
    //metodo de prueba
    public static void main (String [] args) {
        try(Connection conn = Conexion.getConnection()){
            if(conn != null) {
                System.out.println("Conexión exitosa a la base de datos");
            }
        }catch(SQLException e){
            System.out.println("Error al conectarse a la bdd: " + e.getMessage());
        }
    }
}

