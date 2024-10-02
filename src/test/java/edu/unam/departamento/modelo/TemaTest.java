package edu.unam.departamento.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TemaTest {
    private Tema tema;

    @BeforeEach
    public void inicio() {
        tema = new Tema(); 
    }

    @Test
    void testAsignarDescripcion() {
        tema.setDescripcion("Mi tema");
        var actualDescripcion = tema.getDescripcion();
        var esperadaDescripcion = "Mi tema"; 
        assertEquals(esperadaDescripcion, actualDescripcion);
    }

    @Test
    void testAsignarFechaActual() {
        var actualFecha = tema.getFechaInicio();
        var esperadaFecha = LocalDate.now();
        assertEquals(esperadaFecha, actualFecha);
    }

    @Test
    void testAsignarEstadoAbierto() {
        var actualEstado = tema.getEstado();
        var esperadoEstado = Estado.ABIERTO;
        assertEquals(esperadoEstado, actualEstado);
    }
}
