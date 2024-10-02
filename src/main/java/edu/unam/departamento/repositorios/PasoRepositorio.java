package edu.unam.departamento.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.unam.departamento.modelo.Paso;

public interface PasoRepositorio extends JpaRepository<Paso, Integer>{
    
}
