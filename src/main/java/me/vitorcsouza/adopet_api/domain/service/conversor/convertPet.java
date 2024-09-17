package me.vitorcsouza.adopet_api.domain.service.conversor;

import me.vitorcsouza.adopet_api.domain.dto.petDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.petDtoRes;
import me.vitorcsouza.adopet_api.domain.model.Pet;
import me.vitorcsouza.adopet_api.domain.repository.abrigoRepository;
import org.springframework.stereotype.Component;

@Component
public class convertPet {

    public Pet toModel(petDtoReq dto, abrigoRepository repository){
        return new Pet(dto, repository);
    }

    public petDtoRes toDto(Pet pet){
        return new petDtoRes(pet);
    }
}
