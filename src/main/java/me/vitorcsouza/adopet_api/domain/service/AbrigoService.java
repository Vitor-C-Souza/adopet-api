package me.vitorcsouza.adopet_api.domain.service;

import me.vitorcsouza.adopet_api.domain.dto.AbrigoDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.AbrigoDtoRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AbrigoService {
    AbrigoDtoRes create(AbrigoDtoReq dto);
    AbrigoDtoRes findById(Long id);
    Page<AbrigoDtoRes> findAll(Pageable pag);
    AbrigoDtoRes update(AbrigoDtoReq dto, Long id);
    void delete(Long id);
}
