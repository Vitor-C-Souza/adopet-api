package me.vitorcsouza.adopet_api.domain.dto;

import me.vitorcsouza.adopet_api.domain.model.Adocao;

import java.time.LocalDate;

public record AdocaoDtoRes(
        Long id,
        PetDtoRes animal,
        TutorDtoRes tutor,
        LocalDate data
) {
    public AdocaoDtoRes(Adocao adocao){
        this(
                adocao.getId(),
                new PetDtoRes(adocao.getAnimal()),
                new TutorDtoRes(adocao.getTutor()),
                adocao.getData()
        );
    }
}
