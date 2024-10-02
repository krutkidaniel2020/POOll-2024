package edu.unam.departamento.modelo;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AccessLevel;

/** Tipos de temas tratados en el departamento. */
@Entity
@Table(name = "temas")
@Getter @Setter @NoArgsConstructor
public class Tema {
    /** Identificador de tema. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Integer idTema;
    /** Fecha de inicio de un tema en el sistema.*/
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private LocalDate fechaInicio = LocalDate.now();
    /** Fecha de finalización o cierre de un tema en el sistema.*/
    private LocalDate fechaCierre;
    /** Describe la causa por la cual se inicia el tema.*/
    @NotBlank
    @Size(min = 10, max = 255)
    @Column(nullable = false, length = 255)
    private String descripcion;
    /** Estado actual del tema.*/
    @Column(nullable = false)
    private Estado estado = Estado.ABIERTO;
    /** Persona que ha iniciado o solicitado se trate el tema en el departamento.*/
    @NotNull
    @ManyToOne 
    private Persona persona;
    /** Pasos que se han realizado como parte del tratamiento del tema.*/
    @OneToMany (mappedBy = "tema")
    @Setter(AccessLevel.NONE)
    private List<Paso> pasos = new ArrayList<>();
}
