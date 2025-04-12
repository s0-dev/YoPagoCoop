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

    // Muchos valores de atributos pertenecen a un miembro
    // name = "id_miembro" -> nombre de la columna en la tabla
    @ManyToOne
    @JoinColumn(name = "id_miembro", nullable = false)
    private Miembro miembro;

    // Muchos valores de atributos pertenecen a un atributo especifico de escuela
    // name = "id_atributo_especifico_escuela" -> nombre de la columna en la tabla
    @ManyToOne
    @JoinColumn(name = "id_atributo_especifico_escuela", nullable = false)
    private AtributosEscuelas atributoEspecificoEscuela;

    @Column(nullable = false)
    private String valorAtributo;
}
