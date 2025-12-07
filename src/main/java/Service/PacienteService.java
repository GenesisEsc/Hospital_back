package Service;

import Model.Paciente;

import java.util.List;

public interface PacienteService {
    List<Paciente> listar();
    void guardar(Paciente p);
    Paciente buscarPorId(int id);
    Paciente actualizar(Paciente p);
    void cambiarEstado(int id, boolean activo);
}
