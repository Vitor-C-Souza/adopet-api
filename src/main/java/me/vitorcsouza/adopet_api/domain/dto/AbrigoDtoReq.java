package me.vitorcsouza.adopet_api.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AbrigoDtoReq(
        @NotNull @NotBlank String nome,
        @NotNull @NotBlank String email,
        @NotNull @NotBlank String telefone
) {
}
