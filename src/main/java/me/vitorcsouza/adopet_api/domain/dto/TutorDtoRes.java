package me.vitorcsouza.adopet_api.domain.dto;

import me.vitorcsouza.adopet_api.domain.model.Tutor;

public record TutorDtoRes(
        Long id,
        String fotoDePerfil,
        String nome,
        String telefone,
        String cidade,
        String sobre
){
    public TutorDtoRes(Tutor tutor) {
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
