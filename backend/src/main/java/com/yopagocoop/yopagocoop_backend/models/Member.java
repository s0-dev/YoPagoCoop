package com.yopagocoop.yopagocoop_backend.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity

@Table(name = "members")
public class Member {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long school_id;

  // TODO: DNI TEST, string de entre 7 a 9 digitos
  @Column(nullable = false, unique = true, length = 9)
  private String dni;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String lastname;

  // TODO: EMAIL TEST, distintos formatos de email
  @Column(nullable = false)
  private String email;

  // TODO: TELEFONO TEST, distintos formatos de celular
  @Column(nullable = false, length = 20)
  private String phone;

  @Column(nullable = false, updatable = false)
  private LocalDateTime created_at = LocalDateTime.now();

  // Necesitamos Getters y Setters para acceder y modificar los atributos de la

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getSchool_id() {
    return school_id;
  }

  public void setSchool_id(Long school_id) {
    this.school_id = school_id;
  }

  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    this.dni = dni;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public LocalDateTime getCreatedAt() {
    return created_at;
  }

}
