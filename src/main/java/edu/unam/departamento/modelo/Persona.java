package edu.unam.departamento.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AccessLevel;

/**
 * Personas que realizan solicitudes de temas en el departamento.
 */
@Entity
@Getter @Setter @NoArgsConstructor
public class Persona {
    /** 
    * Valor que identifica de manera única a una persona.
    */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Integer idPersona;
    /** 
    * Nombres de la persona según documento.
    */
    @NotBlank
    @Size(min = 2, max = 40)
    @Column(nullable = false, length = 40)
    private String nombres;
    /**
     * Apellidos de la persona según documento.
     */
    @NotBlank
    @Size(min = 2, max = 40)
    @Column(nullable = false, length = 40)
    private String apellidos;
    /** 
     * Define si una persona puede solicitar nuevos temas en el departamento. 
     */
    @NotNull
    @Column(nullable = false)
    private boolean baja = false;
}
