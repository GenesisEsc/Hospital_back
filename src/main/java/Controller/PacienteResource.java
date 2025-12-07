package Controller;
/*
 * Autor: Génesis Escobar
 * Fecha: 06-12-2025
 * Versión: 1.0
 * Descripción:
 * Recurso REST que gestiona las operaciones CRUD de la entidad Paciente.
 * Expone endpoints para listar, buscar, registrar, actualizar y cambiar
 * el estado de los pacientes, delegando la lógica de negocio al servicio.
 * Utiliza la conexión proporcionada por el filtro/servlet para interactuar
 * con la base de datos.
 */
import Model.Paciente;
import Service.PacienteService;
import Service.PacienteServiceImpl;
import Util.HospitalException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.Connection;
import java.util.List;

/**
 * Define la URL base para todos los endpoints relacionados con pacientes.
 */
@Path("/pacientes") //nombre de la url
public class PacienteResource {

    @Context
    HttpServletRequest request; // Permite acceder a la conexión inyectada por el filtro

    /**
     * Endpoint GET para obtener la lista completa de pacientes.
     * Retorna HTTP 200 junto con un arreglo JSON.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        try {
            //1. obtener la conexion
            Connection conn = (Connection) request.getAttribute("conn");

            //2.instanciar el servicio (usando la interfaz)
            PacienteService service = new PacienteServiceImpl(conn);

            //3. llamar a la logica
            List<Paciente> lista = service.listar();

            //4. envolver en respuesta HTTP 200 OK
            return Response.ok(lista).build();

        }catch (Exception e){
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    /**
     * Endpoint GET que permite buscar un paciente por ID.
     * Retorna 200 si existe, o 404 si no se encuentra.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") int id) {
        try {
            Connection conn = (Connection) request.getAttribute("conn");
            PacienteService service = new PacienteServiceImpl(conn);

            Paciente p = service.buscarPorId(id);
            if(p != null) {
                return Response.ok(p).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Paciente no encontrado").build();
            }
        }catch (Exception e){
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    /**
     * Endpoint POST para registrar un nuevo paciente.
     * El servicio valida la cédula y lanza HospitalException si hay error.
     * Respuestas:
     * - 200: creación correcta
     * - 400: datos inválidos
     * - 500: error interno
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response guardar(Paciente p) {
        try {
            Connection conn = (Connection) request.getAttribute("conn");
            PacienteService service = new PacienteServiceImpl(conn);

            service.guardar(p); //esto validará la cedula internamente

            return Response.ok("{\"mensaje\": \"Guardado exitoso\"}").build();
        } catch (HospitalException e) {
            // Si falla la cédula o la BD, devolvemos error 400 (Bad Request)
            return Response.status(400).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }catch (Exception e){
            return Response.status(500).entity("Error interno").build();
        }
    }

    /**
     * Endpoint PUT para actualizar un paciente existente.
     * El ID de la URL tiene prioridad sobre el del cuerpo del JSON.
     * Respuestas:
     * - 200: actualización exitosa
     * - 404: paciente no encontrado
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizar(@PathParam("id")int id, Paciente p) {
        try {
            Connection conn = (Connection) request.getAttribute("conn");
            PacienteService service = new PacienteServiceImpl(conn);

            p.setId(id); //aseguramos que el ID sea el de la url

            Paciente actualizado = service.actualizar(p);

            if (actualizado != null) {
                // Devolvemos 200 OK y el objeto actualizado
                return Response.ok(actualizado).build();
            }else {
                // Devolvemos 404 Not Found si el ID no existe
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Paciente no encontrado\"}")
                        .build();
            }
        }catch (Exception e){
            return Response.status(500).entity(e.getMessage()).build();        }
    }

    /**
     * Endpoint PUT para activar o desactivar un paciente.
     * El estado se recibe mediante un parámetro de consulta (?activo=true/false).
     */
    @PUT
    @Path("/{id}/estado")
    @Produces(MediaType.APPLICATION_JSON)
    public Response cambiarEstado(@PathParam("id")int id, @QueryParam("activo")boolean activo) {
        try {
            Connection conn = (Connection) request.getAttribute("conn");
            PacienteService service = new PacienteServiceImpl(conn);
            service.cambiarEstado(id, activo);

            String mensaje = activo ? "Activado" : "Desactivado";
            return Response.ok("{\"mensaje\": \"Paciente " + mensaje + "\"}").build();
        }catch (Exception e){
            return Response.status(500).entity(e.getMessage()).build();
        }
    }
}
