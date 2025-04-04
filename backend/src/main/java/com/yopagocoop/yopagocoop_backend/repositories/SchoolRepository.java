package com.yopagocoop.yopagocoop_backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yopagocoop.yopagocoop_backend.models.School;

// Indica que esta interfaz es un repositorio de Spring
@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
  // En el repositorio hay que hacer todos los metodos que necesitamos.

  // JpaRepository ya proporciona métodos básicos como:
  // save(), findById(), findAll(), deleteById(), etc.

  // TODO: SCHOOL_REPOSITORY Revisar que funciones integrar en el repositorio

  Optional<School> findByCuit(String cuit);
}
