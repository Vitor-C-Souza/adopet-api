package me.vitorcsouza.adopet_api.domain.service;

import me.vitorcsouza.adopet_api.domain.dto.abrigoDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.abrigoDtoRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface abrigoService {
    abrigoDtoRes create(abrigoDtoReq dto);
    abrigoDtoRes findById(Long id);
    Page<abrigoDtoRes> FindAll(Pageable pag);
    abrigoDtoRes update(abrigoDtoReq dto, Long id);
    void delete(Long id);
}
