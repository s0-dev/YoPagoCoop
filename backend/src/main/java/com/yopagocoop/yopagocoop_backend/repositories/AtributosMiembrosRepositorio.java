package com.yopagocoop.yopagocoop_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.yopagocoop.yopagocoop_backend.models.Miembro;

@Repository
public interface AtributosMiembrosRepositorio extends JpaRepository<Miembro, Long> {

}
