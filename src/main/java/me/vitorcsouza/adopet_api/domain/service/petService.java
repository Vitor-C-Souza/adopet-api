package me.vitorcsouza.adopet_api.domain.service;

import me.vitorcsouza.adopet_api.domain.dto.petDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.petDtoRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface petService {
    petDtoRes create(petDtoReq dto);
    petDtoRes findById(Long id);
    Page<petDtoRes> findAll(Pageable pag);
    petDtoRes update(petDtoReq dto, Long id);
    void delete(Long id);
}
