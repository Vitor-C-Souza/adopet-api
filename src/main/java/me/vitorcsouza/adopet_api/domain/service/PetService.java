package me.vitorcsouza.adopet_api.domain.service;

import me.vitorcsouza.adopet_api.domain.dto.PetDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.PetDtoRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PetService {
    PetDtoRes create(PetDtoReq dto);
    PetDtoRes findById(Long id);
    Page<PetDtoRes> findAll(Pageable pag);
    PetDtoRes update(PetDtoReq dto, Long id);
    void delete(Long id);
}
