package me.vitorcsouza.adopet_api.domain.service;

import me.vitorcsouza.adopet_api.domain.dto.tutorDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.tutorDtoRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface tutorService {
    tutorDtoRes create(tutorDtoReq dto);
    tutorDtoRes findById(Long id);
    Page<tutorDtoRes> FindAll(Pageable pag);
    tutorDtoRes update(tutorDtoReq dto, Long id);
    void delete(Long id);
}
