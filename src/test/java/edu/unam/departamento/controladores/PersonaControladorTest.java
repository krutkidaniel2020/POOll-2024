package edu.unam.departamento.controladores;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import edu.unam.departamento.modelo.Persona;
import edu.unam.departamento.servicios.PersonaServicio;

@WebMvcTest(PersonaControlador.class)
public class PersonaControladorTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonaServicio personaServicio;
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
        ReflectionTestUtils.setField(persona2, "idPersona", 2);
        persona2.setNombres("Jorge");
        persona2.setApellidos("Rodriguez");
        lista = new ArrayList<Persona>();
        lista.add(persona1);
        lista.add(persona2);
    }

    @Test
    public void accesoIndex() throws Exception {

        when(personaServicio.buscarPersonasAlta()).thenReturn(lista);
        mockMvc.perform(get("/personas"))
            .andExpect(status().isOk())
            .andExpect(handler().handlerType(PersonaControlador.class))
            .andExpect(handler().methodName("index"))
            .andExpect(model().attributeExists("personas"))
            .andExpect(model().attribute("personas", hasSize(2)))
            .andExpect(model().attribute("personas", containsInAnyOrder(lista.toArray())))
            .andExpect(view().name("personas"));
    }

/*  @GetMapping("/personas/crear")
    public String nuevaPersona(Model modelo) {
        var persona = new Persona();
        modelo.addAttribute("persona", persona);
        return "nuevaPersona";
    }
 */
    @Test
    public void accesoNuevaPersona() throws Exception {

        mockMvc.perform(get("/personas/crear"))
            .andExpect(status().isOk())
            .andExpect(handler().handlerType(PersonaControlador.class))
            .andExpect(handler().methodName("nuevaPersona"))
            .andExpect(model().attributeExists("persona"))
            .andExpect(view().name("nuevaPersona"));
    }

/*  @PostMapping("/personas")
    public String agregarPersona (@Valid Persona persona, BindingResult resultado) {
        if (resultado.hasErrors()) {
            return "nuevaPersona";
        }
        personaServicio.agregarPersona(persona);
        return "redirect:/personas";    
    }
 */
    @Test
    public void agregoPersona() throws Exception {
        
        doNothing().when(personaServicio).agregarPersona(persona1);    

        mockMvc.perform(post("/personas")
            .param("nombres", persona1.getNombres())
            .param("apellidos", persona1.getApellidos())
            )
            .andDo(print())
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/personas"));
    }
/*
    @GetMapping("/personas/{id}/editar")
    public String editarPersona(@PathVariable("id") Integer id, Model modelo) {
        modelo.addAttribute("persona", personaServicio.buscarPersonaPorId(id));
        return "actualizarPersona";
    }
 */
@Test
public void accesoEditarPersonaPorId() throws Exception {
    
    when(personaServicio.buscarPersonaPorId(persona1.getIdPersona())).thenReturn(persona1);    

    mockMvc.perform(get("/personas/{id}/editar", persona1.getIdPersona()))
        .andDo(print())
        .andExpect(handler().handlerType(PersonaControlador.class))
        .andExpect(handler().methodName("editarPersona"))
        .andExpect(model().attributeExists("persona"))
        .andExpect(model().attribute("persona", persona1))
        .andExpect(status().isOk())
        .andExpect(view().name("actualizarPersona"));
}

 /*
    @PutMapping("/personas/{id}")
    public String actualizarPersona(@PathVariable("id") Integer id, @Valid Persona persona, BindingResult resultado) {
        if (resultado.hasErrors()) {
            return "actualizarPersona";
        }
        personaServicio.actualizarPersonaPorId(id, persona);
        return "redirect:/personas";
    }
 */
@Test
public void editoPersonaPorId() throws Exception {
    
    doNothing().when(personaServicio).actualizarPersonaPorId(persona1.getIdPersona(), persona1);    

    mockMvc.perform(put("/personas/{id}", persona1.getIdPersona())
        .param("nombres", persona1.getNombres())
        .param("apellidos", persona1.getApellidos())
        )
        .andDo(print())
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/personas"));
}

 /*
    @DeleteMapping("/personas/{id}")
    public String eliminarPersona(@PathVariable("id") Integer id) {
        personaServicio.eliminarPersonaPorId(id);
        return "redirect:/personas";
    }
 */
@Test
public void eliminoPersonaPorId() throws Exception {
    
    doNothing().when(personaServicio).eliminarPersonaPorId(persona1.getIdPersona());    

    mockMvc.perform(delete("/personas/{id}", persona1.getIdPersona())
        .param("nombres", persona1.getNombres())
        .param("apellidos", persona1.getApellidos())
        )
        .andDo(print())
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/personas"));
}

}
