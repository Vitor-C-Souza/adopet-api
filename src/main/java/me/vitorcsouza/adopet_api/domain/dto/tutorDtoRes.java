package me.vitorcsouza.adopet_api.domain.dto;

import me.vitorcsouza.adopet_api.domain.model.Tutor;

public record tutorDtoRes(
        Long id,
        String fotoDePerfil,
        String nome,
        String telefone,
        String cidade,
        String sobre
){
    public tutorDtoRes(Tutor tutor) {
        this(
                tutor.getId(),
                tutor.getFotoDePerfil(),
                tutor.getNome(),
                tutor.getTelefone(),
                tutor.getCidade(),
                tutor.getSobre()
        );
    }
}
