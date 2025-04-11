package com.yopagocoop.yopagocoop_backend.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yopagocoop.yopagocoop_backend.modelos.Escuela;

@Repository
public interface RepositorioEscuelas extends JpaRepository<Escuela, Long> {

  Optional<Escuela> findByNombre(String nombre);

  Optional<Escuela> findByEmail(String email);

  Optional<Escuela> findByCuit(String cuit);

}
