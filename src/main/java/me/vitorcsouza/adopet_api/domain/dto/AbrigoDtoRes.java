package me.vitorcsouza.adopet_api.domain.dto;

import me.vitorcsouza.adopet_api.domain.model.Abrigo;

public record AbrigoDtoRes(
        Long id,
        String nome,
        String email,
        String telefone
) {
    public AbrigoDtoRes(Abrigo abrigo) {
        this(
                abrigo.getId(),
                abrigo.getNome(),
                abrigo.getEmail(),
                abrigo.getTelefone()
        );
    }
}
