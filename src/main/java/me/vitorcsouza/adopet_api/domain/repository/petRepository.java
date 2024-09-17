package me.vitorcsouza.adopet_api.domain.repository;

import me.vitorcsouza.adopet_api.domain.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface petRepository extends JpaRepository<Pet, Long> {
}
