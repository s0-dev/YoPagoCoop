package com.yopagocoop.yopagocoop_backend.modelos;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "atributos_especificos_miembros")
@Data
public class AtributosMiembros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private long idMiembro;

    @Column(nullable = false)
    private Long idAtributoEspecificoEscuela;

    @Column(nullable = false)
    private String valorAtributo;
}