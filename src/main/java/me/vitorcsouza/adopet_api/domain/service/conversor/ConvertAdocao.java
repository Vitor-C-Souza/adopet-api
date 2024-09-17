package me.vitorcsouza.adopet_api.domain.service.conversor;

import me.vitorcsouza.adopet_api.domain.dto.AdocaoDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.AdocaoDtoRes;
import me.vitorcsouza.adopet_api.domain.model.Adocao;
import me.vitorcsouza.adopet_api.domain.repository.PetRepository;
import me.vitorcsouza.adopet_api.domain.repository.TutorRepository;
import org.springframework.stereotype.Component;

@Component
public class ConvertAdocao {
    public Adocao toModel(AdocaoDtoReq dto, PetRepository petRepository, TutorRepository tutorRepository){
        return new Adocao(dto, petRepository, tutorRepository);
    }

    public AdocaoDtoRes toDto(Adocao adocao){
        return new AdocaoDtoRes(adocao);
    }
}
