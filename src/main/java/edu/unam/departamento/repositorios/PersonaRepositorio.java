package edu.unam.departamento.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.unam.departamento.modelo.Persona;

@Repository
public interface PersonaRepositorio extends JpaRepository<Persona, Integer> {
    List<Persona> findAllByBaja(boolean baja);
}