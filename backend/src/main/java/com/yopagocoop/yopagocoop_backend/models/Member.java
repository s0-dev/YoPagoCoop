package com.yopagocoop.yopagocoop_backend.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "members")
@Data // genera getter, setters equals, hash y tostring
public class Member {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long school_id;

  // HECHO: DNI TEST, string de entre 7 a 9 digitos
  @Column(nullable = false, unique = true, length = 9)
  private String dni;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String lastname;

  // HECHO: EMAIL TEST, distintos formatos de email
  @Column(nullable = false)
  private String email;

  // HECHO: TELEFONO TEST, distintos formatos de celular
  @Column(nullable = false, length = 20)
  private String phone;

  @Column(nullable = false, updatable = false)
  private LocalDateTime created_at = LocalDateTime.now();
}
