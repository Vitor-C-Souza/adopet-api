package me.vitorcsouza.adopet_api.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record petDtoReq(
        @NotNull @NotBlank String nome,
        @NotNull @NotBlank String descricao,
        @NotNull @NotBlank String idade,
        @NotNull @NotBlank String endereco,
        @NotNull @NotBlank String imagem,
        @NotNull Long abrigos_id,
        boolean adotado
) {
}
