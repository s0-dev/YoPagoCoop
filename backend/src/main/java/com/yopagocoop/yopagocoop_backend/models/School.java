package com.yopagocoop.yopagocoop_backend.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;

// Indica que es una entidad JPA, osea se mapea a una tabla de la db
@Entity
// Especifica el nombre de la tabla en la base de datos
@Table(name = "schools")
public class School {
  @Id // Indica que este atributo es la clave primaria
  @GeneratedValue(strategy = GenerationType.IDENTITY) // genera automáticamente por la base de datos
  private Long id;

  @Column(nullable = false) // No puede ser nulo
  private String name;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false, unique = true, length = 11) // unico y longitud de 11
  private String cuit;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false, updatable = false) // no puede actualizarse una vez se declara
  private LocalDateTime created_at = LocalDateTime.now();

  // Necesitamos Getters y Setters para acceder y modificar los atributos de la
  // clase
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCuit() {
    return cuit;
  }

  public void setCuit(String cuit) {
    this.cuit = cuit;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocalDateTime getCreatedAt() {
    return created_at;
  }
  // El campo 'creado' generalmente no se modifica, así que no necesitamos un
  // setter

}
