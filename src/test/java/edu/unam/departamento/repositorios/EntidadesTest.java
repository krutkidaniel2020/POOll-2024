package edu.unam.departamento.repositorios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import edu.unam.departamento.modelo.Persona;
import edu.unam.departamento.modelo.Estado;
import edu.unam.departamento.modelo.Paso;
import edu.unam.departamento.modelo.Tema;
import edu.unam.departamento.modelo.Tipo;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EntidadesTest {

    @Autowired
    private TestEntityManager entityManager;
    // atributos para test
    private Persona persona;
    private Tema tema1;
    private Tema tema2;
    private Paso paso1;


	@BeforeEach
	public void setUp() {
        // inicio datos persona
        persona = new Persona();
        persona.setNombres("Juan");
        persona.setApellidos("Perez");
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
        Assertions.assertNotNull(entityManager);
    }

    @Test
    public void guardoPersonaLaObtengo() {
        // prueba valor inicial
        assertNull(persona.getIdPersona());
        // guardo persona y retorno valor guardado
        var personaActual = entityManager.persistFlushFind(persona);
        // pruebas
        assertNotNull(personaActual.getIdPersona());
        assertEquals(persona.getNombres(), personaActual.getNombres());
        assertEquals(persona.getApellidos(), personaActual.getApellidos());
    }

    @Test
    public void guardoPersonaLaDoyDeBaja() {
        // guardo persona y retorno valor guardado
        persona = entityManager.persistFlushFind(persona);
        // cambio persona -> baja = true
        persona.setBaja(true);
        // guardo persona y retorno valor guardado
        var personaActual = entityManager.persistFlushFind(persona);
        // prueba
        assertEquals(persona.isBaja(), personaActual.isBaja());
    }

    @Test
    public void guardoTemaSinPasoSeGuardaDescripcion() {
        // pruebas valor inicial
        assertNull(tema1.getIdTema());
        assertEquals(Estado.ABIERTO, tema1.getEstado());
        // asocio tema con persona
        tema1.setPersona(persona);
        // guardo persona
        entityManager.persistAndFlush(persona);
        // guardo tema y retorno valor guardado
        var temaActual = this.entityManager.persistFlushFind(tema1);
        // pruebas
        assertNotNull(temaActual.getIdTema());
        assertNull(temaActual.getFechaCierre());
        assertEquals(tema1.getDescripcion(), temaActual.getDescripcion());
    }

    @Test
    public void guardoTemaSinPasoLargoPasosCero() {
        // pruebas valor inicial
        assertNull(tema1.getIdTema());
        assertEquals(Estado.ABIERTO, tema1.getEstado());
        // asocio tema con persona
        tema1.setPersona(persona);
        // guardo persona
        entityManager.persistAndFlush(persona);
        // guardo tema y retorno valor guardado
        var temaActual = entityManager.persistFlushFind(tema1);
        // pruebas
        assertNotNull(temaActual.getIdTema());
        assertNull(temaActual.getFechaCierre());
        assertEquals(tema1.getPasos().size(), temaActual.getPasos().size());
    }

    @Test
    public void guardoTemaLoCierro() {
        // test inicial
        assertNull(tema1.getFechaCierre());
        // asocio tema con persona
        tema1.setPersona(persona);
        // guardo persona
        entityManager.persistAndFlush(persona);
        // guardo tema y retorno valor guardado
        tema1 = entityManager.persistFlushFind(tema1);
        // cambio tema recuperado a cerrado
        tema1.setEstado(Estado.CERRADO);
        tema1.setFechaCierre(LocalDate.now());
        // guardo y obtengo nuevamente
        var temaActual = this.entityManager.persistFlushFind(tema1);
        // pruebas
        assertNotNull(temaActual.getFechaCierre());
        assertEquals(tema1.getEstado(), temaActual.getEstado());
        assertEquals(tema1.getFechaCierre(), temaActual.getFechaCierre());
    }
    
    @Test
    public void guardoTemaConPasoLargoPasosUno() {
        // prueba valor inicial
        assertNull(paso1.getIdPaso());
        // asocio tema con persona
        tema1.setPersona(persona);
        // asocio paso con tema
        paso1.setTema(tema1);
        tema1.getPasos().add(paso1);
        // guardo persona
        this.entityManager.persistAndFlush(persona);
        // guardo tema y retorno valor guardado
        var temaActual = this.entityManager.persistFlushFind(tema1);
        // guardo paso
        var pasoActual = this.entityManager.persistAndFlush(paso1);
        // pruebas
        assertNotNull(pasoActual.getIdPaso());
        assertEquals(tema1.getPasos().size(), temaActual.getPasos().size());
        assertEquals(1, temaActual.getPasos().size());
    } 

}
