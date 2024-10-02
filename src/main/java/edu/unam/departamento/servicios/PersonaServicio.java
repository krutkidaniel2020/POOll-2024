package edu.unam.departamento.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unam.departamento.modelo.Persona;
import edu.unam.departamento.repositorios.PersonaRepositorio;

@Service
public class PersonaServicio {
    
    @Autowired
    PersonaRepositorio personaRepositorio;

    public PersonaServicio (PersonaRepositorio personaRepositorio) {
        this.personaRepositorio = personaRepositorio;
    }

    public void agregarPersona(Persona persona) {
        personaRepositorio.save(persona);
    }

    public List<Persona> buscarPersonasAlta() {
        return personaRepositorio.findAllByBaja(false);
    }

    public Persona buscarPersonaPorId(int id) {
        return personaRepositorio.findById(id).
            orElseThrow(() -> new EntidadNoEncontradaExcepcion(id));
    }

    public void actualizarPersonaPorId(int id, Persona persona) {
        personaRepositorio.findById(id).
            ifPresent(personaObtenida -> {
                personaObtenida.setApellidos(persona.getApellidos());
                personaObtenida.setNombres(persona.getNombres());
                personaRepositorio.save(personaObtenida);
            });
    }

    public void eliminarPersonaPorId(int id) {
        personaRepositorio.findById(id).
            ifPresent(personaObtenida -> {
                personaObtenida.setBaja(true);
                personaRepositorio.save(personaObtenida);
            });
    }
    
}
