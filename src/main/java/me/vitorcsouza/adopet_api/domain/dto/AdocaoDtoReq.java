package me.vitorcsouza.adopet_api.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdocaoDtoReq(
        @NotNull Long animal,
        @NotNull Long tutor,
        @NotNull @NotBlank String data
) {
}
