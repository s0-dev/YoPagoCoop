package com.yopagocoop.yopagocoop_backend.modelos;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "miembros")
@Data
public class Miembro {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "id_escuela", nullable = false)
  private Escuela escuela;

  @Column(nullable = false)
  private String nombre;

  @Column(nullable = false)
  private String apellido;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false, length = 20)
  private String celular;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime fechaCreacion;

  @OneToMany(mappedBy = "miembro", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<AtributosMiembros> atributosMiembros;
}
