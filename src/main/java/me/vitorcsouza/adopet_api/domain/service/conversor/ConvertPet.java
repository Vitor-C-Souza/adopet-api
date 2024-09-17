package me.vitorcsouza.adopet_api.domain.service.conversor;

import me.vitorcsouza.adopet_api.domain.dto.PetDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.PetDtoRes;
import me.vitorcsouza.adopet_api.domain.model.Pet;
import me.vitorcsouza.adopet_api.domain.repository.AbrigoRepository;
import org.springframework.stereotype.Component;

@Component
public class ConvertPet {

    public Pet toModel(PetDtoReq dto, AbrigoRepository repository){
        return new Pet(dto, repository);
    }

    public PetDtoRes toDto(Pet pet){
        return new PetDtoRes(pet);
    }
}
