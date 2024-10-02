package edu.unam.departamento.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PasoTest {
    private Paso paso;

    @BeforeEach
    public void inicio() {
        paso = new Paso();
    }

    @Test
    void testAsignarDescripcion() {
        paso.setDescripcion("Mi paso");
        var actualDescripcion = paso.getDescripcion();
        var esperadaDescripcion = "Mi paso"; 
        assertEquals(esperadaDescripcion, actualDescripcion);
    }

    @Test
    void testAsignarFechaActual() {
        var actualFecha = paso.getFecha();
        var esperadaFecha = LocalDate.now(); 
        assertEquals(actualFecha, esperadaFecha);
    }

}
