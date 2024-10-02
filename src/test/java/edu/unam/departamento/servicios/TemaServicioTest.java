package edu.unam.departamento.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import edu.unam.departamento.modelo.Persona;
import edu.unam.departamento.modelo.Tema;
import edu.unam.departamento.modelo.Tipo;
import edu.unam.departamento.modelo.Paso;
import edu.unam.departamento.repositorios.PasoRepositorio;
import edu.unam.departamento.repositorios.TemaRepositorio;

@ExtendWith(MockitoExtension.class)
public class TemaServicioTest {
    
    @InjectMocks
    TemaServicio temaServicio;
    @Mock
    TemaRepositorio temaRepositorio;
    @Mock
    PasoRepositorio pasoRepositorio;
    private Persona persona;
    private Tema tema1;
    private Tema tema2;
    private Paso paso1;
    private Paso paso2;
    private List<Tema> lista;

    @BeforeEach
    public void iniciar() {
        // inicio datos persona
        persona = new Persona();
        ReflectionTestUtils.setField(persona, "idPersona", 1);
        persona.setNombres("Juan");
        persona.setApellidos("Perez");
        // inicio datos tema1
        tema1 = new Tema();
        ReflectionTestUtils.setField(tema1, "idTema", 2);
        tema1.setDescripcion("Detalle tema 1");
        tema1.setPersona(persona);
        // inicio datos tema 2
        tema2 = new Tema();
        ReflectionTestUtils.setField(tema2, "idTema", 3);
        tema2.setDescripcion("Detalle tema 2");
        tema2.setPersona(persona);
        // inicio datos paso 1
        paso1 = new Paso();
        ReflectionTestUtils.setField(paso1, "idPaso", 4);
        paso1.setDescripcion("Detalle paso 1");
        paso1.setTema(tema1);
        // tema1.getPasos().add(paso1); Se realiza en servicio
        paso1.setTipo(Tipo.NOTA);
        // inicio datos paso 2
        paso2 = new Paso();
        ReflectionTestUtils.setField(paso2, "idPaso", 5);
        paso2.setDescripcion("Detalle paso 2");
        paso2.setTema(tema1);
        paso2.setTipo(Tipo.TRAMITE);
        lista = new ArrayList<Tema>();
        lista.add(tema1);
        lista.add(tema2);
    }

    @Test
    public void obtengoTemas() {
        // se determina que retorna el mock
        when(temaRepositorio.findAll()).thenReturn(lista);
        // se invoca al servicio
        var temas = temaServicio.buscarTemas();
        // prueba
        assertEquals(2, temas.size());
        // se verifica que el nro. de invocaciones al mock es de 1
        verify(temaRepositorio, times(1)).findAll();
    }

    @Test
    public void agregoTema() {
        // se determina que retorna el mock
        when(temaRepositorio.save(tema1)).thenReturn(tema1);
        // se invoca al servicio
        temaServicio.agregarTema(tema1);
        // se verifica que el nro. de invocaciones al mock es de 1
        verify(temaRepositorio, times(1)).save(tema1);
    }

    @Test
    public void buscoTemaPorId() {
        // se determina que retorna el mock
        when(temaRepositorio.findById(2)).thenReturn(Optional.ofNullable(tema1));
        // se invoca al servicio
        var temaRecuperado = temaServicio.buscarTemaPorId(2);
        // se verifica que el nro. de invocaciones al mock es de 1
        verify(temaRepositorio, times(1)).findById(2);
        // prueba
        assertEquals(tema1, temaRecuperado);
    }

    @Test
    public void buscoTemaPorIdErroneo() {
        // se determina que retorna el mock
        when(temaRepositorio.findById(2)).thenReturn(Optional.empty());
        // prueba invocando al servicio
        assertThrows(EntidadNoEncontradaExcepcion.class, () -> temaServicio.buscarTemaPorId(2));                
        // se verifica que el nro. de invocaciones al mock es de 1
        verify(temaRepositorio, times(1)).findById(2);
    }

    @Test
    public void agregoPasoATema() {
        // se determina que retorna el mock
        when(temaRepositorio.findById(2)).thenReturn(Optional.ofNullable(tema1));
        // se invoca al servicio
        temaServicio.actualizarTemaPorId(2, paso1);
        // se verifica que el nro. de invocaciones al mock es de 1
        verify(temaRepositorio, times(1)).findById(2);
        verify(temaRepositorio, times(1)).save(tema1);
        verify(pasoRepositorio, times(1)).save(paso1);
        // se verifica que no existan otras invocaciones
        verifyNoMoreInteractions(temaRepositorio);
        verifyNoMoreInteractions(pasoRepositorio);
    }

    @Test
    public void cierroTema() {
        // se determina que retorna el mock
        when(temaRepositorio.findById(2)).thenReturn(Optional.ofNullable(tema1));
        // se invoca al servicio
        temaServicio.cerrarTemaPorId(2, tema1);
        // se verifica que el nro. de invocaciones al mock es de 1
        verify(temaRepositorio, times(1)).findById(2);
        verify(temaRepositorio, times(1)).save(tema1);
        // se verifica que no existan otras invocaciones
        verifyNoMoreInteractions(temaRepositorio);
    }

}
