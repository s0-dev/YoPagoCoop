package com.yopagocoop.yopagocoop_backend.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

// Indica que es una entidad JPA, osea se mapea a una tabla de la db
@Entity
// Especifica el nombre de la tabla en la base de datos
@Table(name = "schools")
@Data // genera getter, setters equals, hash y tostring
public class School {
  @Id // Indica que este atributo es la clave primaria
  @GeneratedValue(strategy = GenerationType.IDENTITY) // genera automáticamente por la base de datos
  private Long id;

  @Column(nullable = false) // No puede ser nulo
  private String name;

  @Column(nullable = false)
  private String address;

  // HECHO: CUIT TEST, string de entre 10 a 12 digitos
  @Column(nullable = false, unique = true, length = 11) // unico y longitud de 11
  private String cuit;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false, updatable = false) // no puede actualizarse una vez se declara
  private LocalDateTime created_at = LocalDateTime.now();

}
