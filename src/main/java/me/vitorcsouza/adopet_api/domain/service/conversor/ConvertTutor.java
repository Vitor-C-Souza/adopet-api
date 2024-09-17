package me.vitorcsouza.adopet_api.domain.service.conversor;

import me.vitorcsouza.adopet_api.domain.dto.TutorDtoRes;
import me.vitorcsouza.adopet_api.domain.model.Tutor;
import org.springframework.stereotype.Component;
import me.vitorcsouza.adopet_api.domain.dto.TutorDtoReq;
@Component
public class ConvertTutor {
    public Tutor toModel(TutorDtoReq dto){
        return new Tutor(dto);
    }

    public TutorDtoRes toDto(Tutor tutor){
        return new TutorDtoRes(tutor);
    }
}
