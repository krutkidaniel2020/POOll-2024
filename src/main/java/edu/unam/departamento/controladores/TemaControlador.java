package edu.unam.departamento.controladores;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import edu.unam.departamento.modelo.Paso;
import edu.unam.departamento.modelo.Tema;
import edu.unam.departamento.servicios.PersonaServicio;
import edu.unam.departamento.servicios.TemaServicio;

@Controller
public class TemaControlador {
    
    @Autowired
    TemaServicio temaServicio;
    @Autowired
    PersonaServicio personaServicio;

    public TemaControlador(TemaServicio temaServicio, PersonaServicio personaServicio) {
        this.temaServicio = temaServicio;
        this.personaServicio = personaServicio;
    }

    @GetMapping("/temas")
    public String index(Model modelo) {
        modelo.addAttribute("temas", temaServicio.buscarTemas());
        return "temas";
    }

    @GetMapping("/temas/crear")
    public String nuevoTema(Model modelo) {
        modelo.addAttribute("tema", new Tema());
        modelo.addAttribute("personas", personaServicio.buscarPersonasAlta());
        return "nuevoTema";
    }

    @PostMapping("/temas")
    public String agregarTema (@Valid Tema tema, BindingResult resultado, Model modelo) {
        if (resultado.hasErrors()) {
            modelo.addAttribute("personas", personaServicio.buscarPersonasAlta());    
            return "nuevoTema";
        }
        temaServicio.agregarTema(tema);
        return "redirect:/temas";    
    }

    @GetMapping("/temas/{id}/ver")
    public String editarTema(@PathVariable("id") Integer id, Model modelo) {
        var tema = temaServicio.buscarTemaPorId(id);
        modelo.addAttribute("tema", tema);
        return "actualizarTema";
    }

    @PutMapping("/temas/{id}/ver")
    public String cerrarTema(@PathVariable("id") Integer id, @Valid Tema tema, BindingResult resultado) {
        if (resultado.hasErrors()) {
            return "actualizarTema";
        }
        temaServicio.cerrarTemaPorId(id, tema);
        return "redirect:/temas";
    }

    @GetMapping("/temas/{id}/pasos")
    public String nuevoPaso(@PathVariable("id") Integer id, Model modelo) {
        var tema = temaServicio.buscarTemaPorId(id);
        var paso = new Paso();
        paso.setTema(tema);
        modelo.addAttribute("tema", tema);
        modelo.addAttribute("paso", paso);
        return "nuevoPaso";
    }

    @PostMapping("/temas/{id}/pasos")
    public String agregarPaso(@PathVariable("id") Integer id, @Valid Paso paso, BindingResult resultado) {
        if (resultado.hasErrors()) {
            return "nuevoPaso";
        }
        temaServicio.actualizarTemaPorId(id, paso);
        return "redirect:/temas/{id}/ver";
    }

}
