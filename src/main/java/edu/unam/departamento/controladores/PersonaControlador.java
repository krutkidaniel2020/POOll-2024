package edu.unam.departamento.controladores;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import edu.unam.departamento.modelo.Persona;
import edu.unam.departamento.servicios.PersonaServicio;

@Controller
public class PersonaControlador {
    
    @Autowired
    PersonaServicio personaServicio;

    public PersonaControlador(PersonaServicio personaServicio) {
        this.personaServicio = personaServicio;
    }

    @GetMapping("/personas")
    public String index(Model modelo) {
        var personas = personaServicio.buscarPersonasAlta();
        modelo.addAttribute("personas", personas);
        return "personas";
    }

    @GetMapping("/personas/crear")
    public String nuevaPersona(Model modelo) {
        var persona = new Persona();
        modelo.addAttribute("persona", persona);
        return "nuevaPersona";
    }

    @PostMapping("/personas")
    public String agregarPersona (@Valid Persona persona, BindingResult resultado) {
        if (resultado.hasErrors()) {
            return "nuevaPersona";
        }
        personaServicio.agregarPersona(persona);
        return "redirect:/personas";    
    }

    @GetMapping("/personas/{id}/editar")
    public String editarPersona(@PathVariable("id") Integer id, Model modelo) {
        modelo.addAttribute("persona", personaServicio.buscarPersonaPorId(id));
        return "actualizarPersona";
    }

    @PutMapping("/personas/{id}")
    public String actualizarPersona(@PathVariable("id") Integer id, @Valid Persona persona, BindingResult resultado) {
        if (resultado.hasErrors()) {
            return "actualizarPersona";
        }
        personaServicio.actualizarPersonaPorId(id, persona);
        return "redirect:/personas";
    }

    @DeleteMapping("/personas/{id}")
    public String eliminarPersona(@PathVariable("id") Integer id) {
        personaServicio.eliminarPersonaPorId(id);
        return "redirect:/personas";
    }
}
