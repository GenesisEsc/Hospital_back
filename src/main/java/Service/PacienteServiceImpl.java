package Service;
/*
 * Autor: Génesis Escobar
 * Fecha: 06-12-2025
 * Versión: 1.0
 * Descripción:
 * Esta clase contiene la lógica de negocio asociada a los pacientes,
 * incluyendo validaciones y manejo de excepciones. La capa Service se
 * encarga de coordinar las operaciones entre el controlador y el
 * repositorio, garantizando reglas de negocio antes de acceder a la
 * base de datos.
 * Se inyecta una conexión JDBC mediante el constructor, y cada método
 * instancia su propio PacienteRepository para interactuar con la
 * capa de datos.
 */
import Model.Paciente;
import Repository.PacienteRepository;
import Util.HospitalException;
import Util.ValidadorCedula;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PacienteServiceImpl implements PacienteService {

    /** Conexión activa hacia la base de datos. */
    private final Connection conn;

    /**
     * Constructor que recibe la conexión a la base de datos.
     *
     */
    public PacienteServiceImpl(Connection conn) {
        this.conn = conn;
    }

    /**
     * Obtiene la lista completa de pacientes desde el repositorio.
     * @return lista de pacientes.
     */
    @Override
    public List<Paciente> listar() {
        try {
            PacienteRepository pacienteRepository = new PacienteRepository(conn);
            return pacienteRepository.listar();
        } catch (Exception e) {
            throw new HospitalException("Error al listar pacientes", e);
        }
    }

    /**
     * Guarda un nuevo paciente en el sistema.
     * Incluye la validación obligatoria de cédula antes de persistir.
     * @param p paciente a registrar.
     */
    @Override
    public void guardar(Paciente p) {
        // Regla de negocio: la cédula debe ser válida
        if (!ValidadorCedula.esCedulaValida(p.getCedula())) {
            throw new HospitalException("Cedula ingresada invalida");
        }
        try {
            PacienteRepository pacienteRepository = new PacienteRepository(conn);
            pacienteRepository.guardar(p);
        } catch (SQLException e) {
            throw new HospitalException("Error al guardar en base de datos", e);
        }
    }

    /**
     * Busca un paciente por su ID.
     * @param id identificador del paciente.
     * @return paciente encontrado o null si no existe.
     */
    @Override
    public Paciente buscarPorId(int id) {
        try {
            PacienteRepository pacienteRepository = new PacienteRepository(conn);
            return pacienteRepository.buscarPorId(id);
        }catch (SQLException e) {
            throw new HospitalException("Error al buscar paciente", e);
        }
    }

    /**
     * Actualiza la información de un paciente existente.
     * También valida la cédula antes de ejecutar la acción.
     * @param p paciente con datos actualizados.
     * @return paciente modificado, o null si no se logró actualizar.
     */
    @Override
    public Paciente actualizar(Paciente p) {
        // Validación previa obligatoria
        if (!ValidadorCedula.esCedulaValida(p.getCedula())) {
            throw new HospitalException("Cedula ingresada invalida");
        }
        try{
            PacienteRepository pacienteRepository = new PacienteRepository(conn);
            return pacienteRepository.actualizar(p);
        } catch (SQLException e) {
            throw new HospitalException("Error al actualizar paciente", e);
        }
    }

    /**
     * Cambia el estado activo/inactivo de un paciente.
     * ara "eliminación lógica" sin perder el registro.
     * @param id identificador del paciente.
     * @param activo nuevo estado a establecer.
     */
    @Override
    public void cambiarEstado(int id, boolean activo) {
        try{
            PacienteRepository pacienteRepository = new PacienteRepository(conn);
            pacienteRepository.actualizarEstado(id, activo);
        }catch (SQLException e) {
            throw new HospitalException("Error al cambiar estado", e);
        }
    }
}
