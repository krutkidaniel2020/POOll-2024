package edu.unam.departamento.repositorios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import edu.unam.departamento.modelo.Persona;
import edu.unam.departamento.modelo.Paso;
import edu.unam.departamento.modelo.Tema;
import edu.unam.departamento.modelo.Tipo;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositorioTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private PersonaRepositorio personaRepositorio;
    @Autowired
    private TemaRepositorio temaRepositorio;
    @Autowired
    private PasoRepositorio pasoRepositorio;
    // atributos para test
    private Persona persona1;
    private Persona persona2;
    private Tema tema1;
    private Tema tema2;
    private Paso paso1;

    @BeforeEach
    public void inicio() {
        // inicio datos persona1
        persona1 = new Persona();
        persona1.setNombres("Juan");
        persona1.setApellidos("Perez");
        // inicio datos persona2
        persona2 = new Persona();
        persona2.setNombres("Jorge");
        persona2.setApellidos("Rodriguez");
        // inicio datos tema1
        tema1 = new Tema();
        tema1.setDescripcion("Estado general");
        // inicio datos tema2
        tema2 = new Tema();
        tema2.setDescripcion("Estado parcial");
        // inicio datos paso1
        paso1 = new Paso();
        paso1.setDescripcion("Mi primer nota");
        paso1.setTipo(Tipo.NOTA);
    }

    @Test
    public void cargaContexto() {
        assertNotNull(entityManager);
        assertNotNull(personaRepositorio);
        assertNotNull(temaRepositorio);
        assertNotNull(pasoRepositorio);
    }

    @Test
    public void noEncuentroPersonasSiRepositorioEstaVacio() {
        var personas = personaRepositorio.findAll();
        assertTrue(personas.isEmpty());
    }

    @Test
    public void almacenoPersona() {
        assertNull(persona1.getIdPersona());
        var personaActual = personaRepositorio.save(persona1);
        assertNotNull(personaActual.getIdPersona());
        assertEquals(false, personaActual.isBaja());
    }
  
    @Test
    public void encuentroTodasLasPersonas() {
        personaRepositorio.save(persona1);
        personaRepositorio.save(persona2);
        var personas = personaRepositorio.findAll();
        assertEquals(2, personas.size());
        assertTrue(personas.containsAll(List.of(persona1, persona2)));
    }

    @Test
    public void encuentroPersonaPorId() {
        personaRepositorio.save(persona1);
        personaRepositorio.save(persona2);  
        var personaActual = personaRepositorio.findById(persona2.getIdPersona()).get();
        assertEquals(personaActual.getIdPersona(), persona2.getIdPersona());
    }
   
    @Test
    public void encuentroPersonasDeAlta() {
        personaRepositorio.save(persona1);
        persona2.setBaja(true);
        personaRepositorio.save(persona2);
        var personasAlta = personaRepositorio.findAllByBaja(false);
        assertTrue(personasAlta.containsAll(List.of(persona1)));
        assertEquals(1, personasAlta.size());
    }

    @Test
    public void actualizoPersonaPorId() {
        personaRepositorio.save(persona1);
        personaRepositorio.save(persona2);
        var personaRecuperada = personaRepositorio.findById(persona2.getIdPersona()).get();
        personaRecuperada.setBaja(true);
        personaRepositorio.save(personaRecuperada);
        var personaActual = personaRepositorio.findById(personaRecuperada.getIdPersona()).get();
        assertEquals(personaRecuperada, personaActual);
    }

    @Test
    public void eliminoPersonaPorId() {
        personaRepositorio.save(persona1);
        personaRepositorio.save(persona2);
        personaRepositorio.deleteById(persona2.getIdPersona());
        var personas = personaRepositorio.findAll();
        assertEquals(1, personas.size());
    }

    @Test
    public void eliminoTodasPersonas() {
        personaRepositorio.save(persona1);
        personaRepositorio.save(persona2);
        personaRepositorio.deleteAll();
        var personas = personaRepositorio.findAll();
        assertTrue(personas.isEmpty());
    }

    @Test
    public void noEncuentroTemasSiRepositorioEstaVacio() {
        List<Tema> temas = temaRepositorio.findAll();
        assertTrue(temas.isEmpty());
    }

    @Test
    public void noEncuentroPasosSiRepositorioEstaVacio() {
        List<Paso> pasos = pasoRepositorio.findAll();
        assertTrue(pasos.isEmpty());
    }

}
