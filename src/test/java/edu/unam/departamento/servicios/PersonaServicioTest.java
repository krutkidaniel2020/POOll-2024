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
import edu.unam.departamento.repositorios.PersonaRepositorio;

/*
 * Por norma general para generar los tests unitarios sobre los servicios
 * se deben definir tests que funcionen con la extensión de Mockito 
 * @ExtendWith(MockitoExtension.class). 
 * 
 * Estos tests no levantan ningún contexto de Spring por lo que su tiempo de 
 * ejecución es en general rápido. 
 * 
 * En este tipo de tests los mocks (objetos simulados) deben de inicializarse 
 * con la anotación de @Mock mientras que la clase sobre la que se realizan 
 * los tests debe de anotarse con @InjectMocks .
 */

@ExtendWith(MockitoExtension.class)
public class PersonaServicioTest {

    @InjectMocks
    PersonaServicio personaServicio;
    @Mock
    PersonaRepositorio personaRepositorio;
    // atributos de pruebas
    private Persona persona1;
    private Persona persona2;
    private List<Persona> lista;

    @BeforeEach
    public void iniciar() {
        // inicio datos persona1
        persona1 = new Persona();
        ReflectionTestUtils.setField(persona1, "idPersona", 1);
        persona1.setNombres("Juan");
        persona1.setApellidos("Perez");
        // inicio datos persona2
        persona2 = new Persona();
        ReflectionTestUtils.setField(persona2, "idPersona",2);
        persona2.setNombres("Jorge");
        persona2.setApellidos("Rodriguez");
        lista = new ArrayList<Persona>();
        lista.add(persona1);
        lista.add(persona2);
    }

    @Test
    public void obtengoPersonasDeAlta() {
        // se determina que retorna el mock
        when(personaRepositorio.findAllByBaja(false)).thenReturn(lista);
        // se invoca al servicio
        var personas = personaServicio.buscarPersonasAlta();
        // prueba
        assertEquals(2, personas.size());
        // se verifica que el nro. de invocaciones al mock es de 1
        verify(personaRepositorio, times(1)).findAllByBaja(false);
    }

    @Test
    public void agregoPersona() {
        // se determina que retorna el mock
        when(personaRepositorio.save(persona1)).thenReturn(persona1);
        // se invoca al servicio
        personaServicio.agregarPersona(persona1);
        // se verifica que el nro. de invocaciones al mock es de 1
        verify(personaRepositorio, times(1)).save(persona1);
    }

    @Test
    public void buscoPersonaPorId() {
        // se determina que retorna el mock
        when(personaRepositorio.findById(1)).thenReturn(Optional.ofNullable(persona1));
        // se invoca al servicio
        var personaRecuperada = personaServicio.buscarPersonaPorId(1);
        // se verifica que el nro. de invocaciones al mock es de 1
        verify(personaRepositorio, times(1)).findById(1);
        // prueba
        assertEquals(persona1, personaRecuperada);
    }

    @Test
    public void buscoPersonaPorIdErroneo() {
        // se determina que retorna el mock
        when(personaRepositorio.findById(1)).thenReturn(Optional.empty());
        // prueba invocando al servicio
        assertThrows(EntidadNoEncontradaExcepcion.class, () -> personaServicio.buscarPersonaPorId(1));                
        // se verifica que el nro. de invocaciones al mock es de 1
        verify(personaRepositorio, times(1)).findById(1);
    }

    @Test
    public void editoPersonaPorId() {
        // se determina que retorna el mock
        when(personaRepositorio.findById(1)).thenReturn(Optional.of(persona1));
        // se invoca al servicio
        personaServicio.actualizarPersonaPorId(1, persona1);
        // se verifica el nro. de invocaciones al mock
        verify(personaRepositorio, times(1)).findById(1);
        verify(personaRepositorio, times(1)).save(persona1);
        // se verifica que no existan otras invocaciones
        verifyNoMoreInteractions(personaRepositorio);
    }

    @Test
    public void eliminoPersonaPorId() {
        // se determina que retorna el mock
        when(personaRepositorio.findById(1)).thenReturn(Optional.of(persona1));
        // se invoca al servicio
        personaServicio.eliminarPersonaPorId(1);
        // se verifica el nro. de invocaciones al mock
        verify(personaRepositorio, times(1)).findById(1);
        verify(personaRepositorio, times(1)).save(persona1);
        // se verifica que no existan otras invocaciones
        verifyNoMoreInteractions(personaRepositorio);
    }

}
