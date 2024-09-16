package me.vitorcsouza.adopet_api.domain.service.conversor;

import me.vitorcsouza.adopet_api.domain.dto.tutorDtoRes;
import me.vitorcsouza.adopet_api.domain.model.Tutor;
import org.springframework.stereotype.Component;
import me.vitorcsouza.adopet_api.domain.dto.tutorDtoReq;
@Component
public class convertTutor {
    public Tutor toModel(tutorDtoReq dto){
        return new Tutor(dto);
    }

    public tutorDtoRes toDto(Tutor tutor){
        return new tutorDtoRes(tutor);
    }
}
