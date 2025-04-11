package com.yopagocoop.yopagocoop_backend.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yopagocoop.yopagocoop_backend.modelos.Miembro;

@Repository
public interface RepositorioMiembros extends JpaRepository<Miembro, Long> {

  // TODO: MEMBER_REPOSITORY Revisar que funciones integrar en el repositorio

}
