package edu.unam.departamento.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonaTest {
    private Persona persona;

    @BeforeEach
    public void inicio() {
        persona = new Persona();
    }

    @Test
    public void testAsignarNombresApellidos() {
        persona.setNombres("Juan");
        persona.setApellidos("Perez");
        var actualNombres = persona.getNombres();
        var actualApellidos = persona.getApellidos();
        var esperadoNombres = "Juan"; 
        var esperadoApellidos = "Perez"; 
        assertEquals(esperadoNombres, actualNombres);
        assertEquals(esperadoApellidos, actualApellidos);
    }

}
