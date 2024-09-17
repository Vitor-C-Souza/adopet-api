package me.vitorcsouza.adopet_api.domain.service;

import me.vitorcsouza.adopet_api.domain.dto.TutorDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.TutorDtoRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface TutorService {
    TutorDtoRes create(TutorDtoReq dto);
    TutorDtoRes findById(Long id);
    Page<TutorDtoRes> findAll(Pageable pag);
    TutorDtoRes update(TutorDtoReq dto, Long id);
    void delete(Long id);
}
