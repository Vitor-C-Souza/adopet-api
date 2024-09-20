package me.vitorcsouza.adopet_api.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginDto(
        @NotBlank @NotNull String usuario,
        @NotBlank @NotNull String senha
) {
}
