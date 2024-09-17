package me.vitorcsouza.adopet_api.domain.repository;

import me.vitorcsouza.adopet_api.domain.model.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query(value = "SELECT * FROM pet_tb WHERE pet_tb.adotado = 0", nativeQuery = true)
    Page<Pet> findAllAvailable(Pageable pag);

    @Query(value = "SELECT * FROM pet_tb WHERE pet_tb.adotado = 0 AND pet_tb.id = :idBusca", nativeQuery = true)
    Optional<Pet> findByIdAvailable(Long idBusca);
}
