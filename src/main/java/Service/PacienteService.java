package Service;
/*
 * Autor: Génesis Escobar
 * Fecha: 06-12-2025
 * Versión: 1.0
 * Descripción:
 * Interfaz que define los métodos del servicio encargado de manejar
 * la lógica de negocio relacionada con los pacientes.
 * La idea del Service es separar la lógica de negocio de la capa Repository.
 * Aquí solo declaramos QUÉ debe hacer el servicio, no CÓMO lo hace.
 * La implementación real estará en una clase PacienteServiceImpl.
 */
import Model.Paciente;

import java.util.List;

public interface PacienteService {
    /**
     * Obtiene la lista completa de pacientes.
     * Llama al repository para traer todos los registros.
     * @return lista de pacientes existentes en la BD.
     */
    List<Paciente> listar();
    /**
     * Guarda un nuevo paciente en la base de datos.
     * Se usa para operaciones tipo POST.
     * @param p objeto Paciente con los datos a registrar.
     */
    void guardar(Paciente p);
    /**
     * Busca un paciente por su identificador.
     * Se usa en peticiones GET /{id}.
     * @param id identificador del paciente.
     * @return un Paciente si existe, o null si no se encontró.
     */
    Paciente buscarPorId(int id);
    /**
     * Actualiza los datos generales de un paciente.
     * Se usa para peticiones PUT /{id}.
     * @param p paciente con los campos ya modificados.
     * @return el paciente actualizado, o null si no se realizó la operación.
     */
    Paciente actualizar(Paciente p);
    /**
     * Activa o desactiva un paciente.
     * Es una actualización parcial y útil para inhabilitar registros sin borrarlos.
     * @param id identificador del paciente.
     * @param activo nuevo estado (true = activo, false = inactivo).
     */
    void cambiarEstado(int id, boolean activo);
}
