package edu.unam.departamento.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.unam.departamento.modelo.Tema;

public interface TemaRepositorio extends JpaRepository<Tema, Integer> {
    
}
