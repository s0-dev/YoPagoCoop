package com.yopagocoop.yopagocoop_backend.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "atributos_especificos_escuelas")
@Data
public class AtributosEscuelas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idEscuela;
    
    @Column(nullable = false)
    private String nombreAtributo;

    @Column(nullable = false)
    private String tipoDato;

    @Column(nullable = false)
    private Boolean esRequerido;

}