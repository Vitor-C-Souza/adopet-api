package me.vitorcsouza.adopet_api.domain.repository;

import me.vitorcsouza.adopet_api.domain.model.Abrigo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbrigoRepository extends JpaRepository<Abrigo, Long> {
}
