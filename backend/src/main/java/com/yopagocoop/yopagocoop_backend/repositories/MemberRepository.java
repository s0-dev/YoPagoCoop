package com.yopagocoop.yopagocoop_backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yopagocoop.yopagocoop_backend.models.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

  // TODO: MEMBER_REPOSITORY Revisar que funciones integrar en el repositorio

  Optional<Member> findByDni(String dni);

}
