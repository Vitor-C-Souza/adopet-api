package me.vitorcsouza.adopet_api.domain.service.conversor;

import me.vitorcsouza.adopet_api.domain.dto.AbrigoDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.AbrigoDtoRes;
import me.vitorcsouza.adopet_api.domain.model.Abrigo;
import org.springframework.stereotype.Component;

@Component
public class ConvertAbrigo {
    public Abrigo toModel(AbrigoDtoReq dto){
        return new Abrigo(dto);
    }

    public AbrigoDtoRes toDto(Abrigo abrigo){
        return new AbrigoDtoRes(abrigo);
    }
}
