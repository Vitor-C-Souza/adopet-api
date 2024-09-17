package me.vitorcsouza.adopet_api.domain.dto;

import me.vitorcsouza.adopet_api.domain.model.Pet;

public record petDtoRes(
        Long id,
        String nome,
        String descricao,
        boolean adotado,
        String idade,
        String endereco,
        String imagem,
        abrigoDtoRes abrigo
) {
    public petDtoRes(Pet pet){
        this(
                pet.getId(),
                pet.getNome(),
                pet.getDescricao(),
                pet.isAdotado(),
                pet.getIdade(),
                pet.getEndereco(),
                pet.getImagem(),
                new abrigoDtoRes(pet.getAbrigo())
        );
    }
}
