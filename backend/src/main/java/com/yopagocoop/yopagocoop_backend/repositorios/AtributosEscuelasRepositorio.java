package com.yopagocoop.yopagocoop_backend.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yopagocoop.yopagocoop_backend.modelos.AtributosEscuelas;

public interface AtributosEscuelasRepositorio extends JpaRepository<AtributosEscuelas, Long> {
  List<AtributosEscuelas> findByEscuelaId(Long escuelaId);

  Optional<AtributosEscuelas> findByEscuelaIdAndNombreAtributo(Long escuelaId, String nombreAtributo);
}
