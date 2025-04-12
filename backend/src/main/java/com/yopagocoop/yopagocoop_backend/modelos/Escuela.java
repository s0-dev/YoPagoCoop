package com.yopagocoop.yopagocoop_backend.modelos;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.Data;

// Indica que es una entidad JPA, osea se mapea a una tabla de la db
@Entity
// Especifica el nombre de la tabla en la base de datos
@Table(name = "escuelas")
@Data // genera getter, setters equals, hash y tostring
public class Escuela {
  @Id // Indica que este atributo es la clave primaria
  @GeneratedValue(strategy = GenerationType.IDENTITY) // genera automáticamente por la base de datos
  private Long id;

  @Column(nullable = false) // No puede ser nulo
  private String nombre;

  @Column(nullable = false)
  private String direccion;

  // HECHO: CUIT TEST, string de entre 10 a 12 digitos
  @Column(nullable = false, unique = true, length = 11) // unico y longitud de 11
  private String cuit;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false, updatable = false) // no puede actualizarse una vez se declara
  @CreationTimestamp
  private LocalDateTime fechaCreacion = LocalDateTime.now();

  // Una escuela tiene muchos atributos especificos
  // mappedBy = "escuela" -> hace referencia al nombre del atributo en la clase
  // AtributosEscuelas
  // cascade = CascadeType.ALL -> Las operaciones que se realicen en la escuela se
  // aplicaran a sus atributos
  // orphanRemoval = true -> Cuando se borre la escuela se borraran sus atributos
  @OneToMany(mappedBy = "escuela", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<AtributosEscuelas> atributosEscuelas;

  // Una escuela tiene muchos miembros
  // mappedBy = "escuela" -> hace referencia al nombre del atributo en la clase
  // Miembro
  // cascade = CascadeType.ALL -> Las operaciones que se realicen en la escuela se
  // aplicaran a sus miembros
  // orphanRemoval = true -> Cuando se borre la escuela se borraran sus miembros
  @OneToMany(mappedBy = "escuela", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Miembro> miembros;

}
