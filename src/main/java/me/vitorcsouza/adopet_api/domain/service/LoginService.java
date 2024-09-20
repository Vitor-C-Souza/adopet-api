package me.vitorcsouza.adopet_api.domain.service;

import me.vitorcsouza.adopet_api.domain.dto.CadastroDto;

public interface LoginService {
    CadastroDto cadastroAbrigo(CadastroDto dto, String senha);

    CadastroDto cadastroTutor(CadastroDto dto, String senhaEncriptada);
}
