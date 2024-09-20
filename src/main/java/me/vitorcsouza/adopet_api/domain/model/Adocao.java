package me.vitorcsouza.adopet_api.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.vitorcsouza.adopet_api.domain.dto.AdocaoDtoReq;
import me.vitorcsouza.adopet_api.domain.repository.PetRepository;
import me.vitorcsouza.adopet_api.domain.repository.TutorRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

@Entity(name = "adocao_tb")
@Getter
@NoArgsConstructor
public class Adocao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet animal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    @Column(nullable = false)
    private LocalDate data;


    public Adocao(AdocaoDtoReq dto, PetRepository petRepository, TutorRepository tutorRepository) {
        createOrUpdate(dto, petRepository, tutorRepository);
    }

    public void createOrUpdate(AdocaoDtoReq dto, PetRepository petRepository, TutorRepository tutorRepository) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.animal = petRepository.findByIdAvailable(dto.animal()).orElseThrow(NoSuchElementException::new);
        this.tutor = tutorRepository.findById(dto.tutor()).orElseThrow(NoSuchElementException::new);
        this.data = LocalDate.parse(dto.data(), formatter);
    }
}
