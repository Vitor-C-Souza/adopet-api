package me.vitorcsouza.adopet_api.domain.service.conversor;

import me.vitorcsouza.adopet_api.domain.dto.abrigoDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.abrigoDtoRes;
import me.vitorcsouza.adopet_api.domain.model.Abrigo;
import org.springframework.stereotype.Component;

@Component
public class convertAbrigo {
    public Abrigo toModel(abrigoDtoReq dto){
        return new Abrigo(dto);
    }

    public abrigoDtoRes toDto(Abrigo abrigo){
        return new abrigoDtoRes(abrigo);
    }
}
