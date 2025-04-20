package com.yopagocoop.yopagocoop_backend.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yopagocoop.yopagocoop_backend.modelos.AtributosMiembros;
import com.yopagocoop.yopagocoop_backend.modelos.Miembro;

import java.util.List;

@Repository
public interface AtributosMiembrosRepositorio extends JpaRepository<AtributosMiembros, Long> {
  List<AtributosMiembros> findByMiembro(Miembro miembro);

  void deleteByMiembro(Miembro miembro);
}