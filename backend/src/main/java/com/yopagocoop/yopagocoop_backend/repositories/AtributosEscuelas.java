package com.yopagocoop.yopagocoop_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yopagocoop.yopagocoop_backend.models.School;

public interface AtributosEscuelas extends JpaRepository<School, Long> {

}
