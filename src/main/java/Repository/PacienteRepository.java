package Repository;
/*
 * Autor: Génesis Escobar
 * Fecha: 06-12-2025
 * Versión: 1.0
 * Descripción:
 * Repositorio encargado de gestionar el acceso a datos de la entidad Paciente.
 * Implementa operaciones CRUD utilizando JDBC y recibe la conexión desde
 * el filtro de conexión. Centraliza toda la interacción con la base de datos,
 * manteniendo aislada la lógica SQL del resto del sistema.
 */
import Model.Paciente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PacienteRepository {

    // Conexión proporcionada por el filtro; permite ejecutar las consultas SQL
    private Connection conn;

    /**
     * Constructor que obliga a quien use este repositorio a proporcionar la conexión.
     * Esto facilita el manejo transaccional controlado desde el filtro.
     */
    public PacienteRepository(Connection conn) {
        this.conn = conn;
    }

    /**
     * Obtiene todos los pacientes registrados en la base de datos.
     * Usado en el metodo GET de la API.
     *
     * @return Lista completa de pacientes.
     */
    public List<Paciente> listar() throws SQLException {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM paciente";

        // try-with-resources garantiza cierre automático de PreparedStatement y ResultSet
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Paciente p = mapearPaciente(rs);
                lista.add(p);
            }
        }
        return lista;
    }

    /**
     * Inserta un nuevo paciente en la base de datos.
     * Parámetros con '?' protegen contra SQL Injection.
     */
    public void guardar(Paciente p) throws SQLException {
        //consulta sql con signos de interrogacion para evitar sql injection
        String sql = "INSERT INTO paciente (nombre, cedula, correo, edad, direccion, activo) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getCedula());
            stmt.setString(3, p.getCorreo());
            stmt.setInt(4, p.getEdad());
            stmt.setString(5, p.getDireccion());
            stmt.setBoolean(6, true); //por defecto al crear está activo

            stmt.executeUpdate();//ejecuta el insert
        }
    }

    /**
     * Busca un paciente por su ID.
     * Devuelve el objeto si existe o null si no se encontró.
     */
    public Paciente buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM paciente WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearPaciente(rs);
                }
            }
        }
        return null; //si no existe
    }
    /**
     * Actualiza los datos de un paciente existente.
     * Si se modifica correctamente, retorna el objeto; caso contrario, null.
     */
    public Paciente actualizar(Paciente p) throws SQLException {
        String sql = "UPDATE paciente SET nombre=?, cedula=?, correo=?, edad=?, direccion=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getCedula());
            stmt.setString(3, p.getCorreo());
            stmt.setInt(4, p.getEdad());
            stmt.setString(5, p.getDireccion());
            stmt.setInt(6, p.getId());
            int filasAfectadas = stmt.executeUpdate();

            // Si se actualizó algo, devolvemos el objeto. Si no, devolvemos null.
            if (filasAfectadas > 0) {
                return p;
            } else {
                return null;
            }
        }
    }
    /**
     * Cambia el estado del paciente (activo/desactivado).
     * Utilizado por la API en operaciones PUT específicas.
     */
    public void actualizarEstado(int id, boolean activo) throws SQLException {
        String sql = "Update paciente SET activo=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, activo);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    /**
     * Metodo auxiliar que convierte una fila del ResultSet en un objeto Paciente.
     * Permite evitar duplicación de código y mantener limpieza en los métodos CRUD.
     */
    private Paciente mapearPaciente(ResultSet rs) throws SQLException {
        Paciente p = new Paciente();
        p.setId(rs.getInt("id"));
        p.setNombre(rs.getString("nombre"));
        p.setCedula(rs.getString("cedula"));
        p.setCorreo(rs.getString("correo"));
        p.setEdad(rs.getInt("edad"));
        p.setDireccion(rs.getString("direccion"));
        p.setActivo(rs.getBoolean("activo"));
        return p;
    }
}
