package Service;

import Model.Paciente;
import Repository.PacienteRepository;
import Util.HospitalException;
import Util.ValidadorCedula;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PacienteServiceImpl implements PacienteService {

    private final Connection conn;

    //inyectamos la conexion por constructor
    public PacienteServiceImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Paciente> listar() {
        try {
            PacienteRepository pacienteRepository = new PacienteRepository(conn);
            return pacienteRepository.listar();
        } catch (Exception e) {
            throw new HospitalException("Error al listar pacientes", e);
        }
    }

    @Override
    public void guardar(Paciente p) {
        //logica de negocio (validacion de cedula)
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

    @Override
    public Paciente buscarPorId(int id) {
        try {
            PacienteRepository pacienteRepository = new PacienteRepository(conn);
            return pacienteRepository.buscarPorId(id);
        }catch (SQLException e) {
            throw new HospitalException("Error al buscar paciente", e);
        }
    }

    @Override
    public Paciente actualizar(Paciente p) {
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
