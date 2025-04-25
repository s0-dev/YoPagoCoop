package com.yopagocoop.yopagocoop_backend.repositorios;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.yopagocoop.yopagocoop_backend.modelos.Miembro;

public interface RepositorioMiembros extends JpaRepository<Miembro, Long> {
  List<Miembro> findByEscuelaId(Long escuelaId);

  Optional<Miembro> findByEmail(String email);
}