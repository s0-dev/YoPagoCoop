package com.yopagocoop.yopagocoop_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yopagocoop.yopagocoop_backend.models.Escuela;

// Indica que esta interfaz es un repositorio de Spring
@Repository
public interface RepositoioEscuelas extends JpaRepository<Escuela, Long> {
  // En el repositorio hay que hacer todos los metodos que necesitamos.

  // JpaRepository ya proporciona métodos básicos como:
  // save(), findById(), findAll(), deleteById(), etc.

  // TODO: SCHOOL_REPOSITORY Revisar que funciones integrar en el repositorio
  // Agregar una query la cual genere atributos especificos por escuela
  // Agregar una query la cual muestre todos los atributos especificos de la
  // escuela seleccionada via ID

}
