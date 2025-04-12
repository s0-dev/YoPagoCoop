package com.yopagocoop.yopagocoop_backend.modelos;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "atributos_especificos_escuelas")
@Data
public class AtributosEscuelas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_escuela", nullable = false)
    private Escuela escuela;

    @Column(nullable = false)
    private String nombreAtributo;

    @Column(nullable = false)
    private String tipoDato;

    @Column(nullable = false)
    private Boolean esRequerido;

    // Un atributo puede tener muchos valores de miembros
    @OneToMany(mappedBy = "atributoEspecificoEscuela", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AtributosMiembros> atributosMiembros;
}
