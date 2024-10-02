package edu.unam.departamento.modelo;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AccessLevel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="pasos")
@Getter @Setter @NoArgsConstructor
public class Paso {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    @Column(updatable = false)
    private Integer idPaso;
    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private LocalDate fecha = LocalDate.now();
    @NotBlank
    @Size(min = 10, max = 255)
    @Column(nullable = false, length = 255)
    private String descripcion;
    @NotNull
    private Tipo tipo;
    @NotNull
    @ManyToOne
    private Tema tema;
}
