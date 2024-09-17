package me.vitorcsouza.adopet_api.domain.service;

import me.vitorcsouza.adopet_api.domain.dto.AdocaoDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.AdocaoDtoRes;

public interface AdocaoService {
    AdocaoDtoRes create(AdocaoDtoReq dto);

    void delete(Long id);
}
