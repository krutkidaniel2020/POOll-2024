package edu.unam.departamento.servicios;

public class EntidadNoEncontradaExcepcion extends RuntimeException {
    EntidadNoEncontradaExcepcion(int id) {
        super("Entidad no encontrada: " + id);
    }
}