package Repository;

import Model.Paciente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PacienteRepository {

    //variable que guardará la conexion que recibimos del Filtro
    private Connection conn;

    //Constructor, obligamos a quien use esta clase a darnos la conexion
    public PacienteRepository(Connection conn) {
        this.conn = conn;
    }

    //1.Listar todos los pacientes (Para GET)
    public List<Paciente> listar() throws SQLException {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM paciente";

        //try-with-resources: cierra el preparedStatement automaticamente
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Paciente p = mapearPaciente(rs);
                lista.add(p);
            }
        }
        return lista;
    }

    //2.Guardar (para POST)
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

    //3. buscar por id(GET{ID})
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
    //4. actualizar (para PUT{ID})
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
    //5. cambiar estado (PUT desactivar/activar)
    public void actualizarEstado(int id, boolean activo) throws SQLException {
        String sql = "Update paciente SET activo=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, activo);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    //metodo auxiliar para no repetir codigo al leer del resultset
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
