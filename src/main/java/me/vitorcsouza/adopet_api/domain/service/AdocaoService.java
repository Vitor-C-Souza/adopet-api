package me.vitorcsouza.adopet_api.domain.service;

import me.vitorcsouza.adopet_api.domain.dto.AdocaoDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.AdocaoDtoRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdocaoService {
    AdocaoDtoRes create(AdocaoDtoReq dto);

    void delete(Long id);

    Page<AdocaoDtoRes> findAll(Pageable paginacao);
}
