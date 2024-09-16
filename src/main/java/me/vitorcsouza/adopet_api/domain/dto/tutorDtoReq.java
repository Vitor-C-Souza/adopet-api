package me.vitorcsouza.adopet_api.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record tutorDtoReq(
        @NotNull @NotBlank(message = "A foto de perfil deve ser preenchida") String fotoDePerfil,
        @NotNull @NotBlank(message = "O nome deve ser preenchido") String nome,
        @NotNull @NotBlank(message = "O telefone deve ser preenchido") String telefone,
        @NotNull @NotBlank(message = "A cidade deve ser preenchida") String cidade,
        @NotNull @NotBlank(message = "O sobre deve ser preenchido") @Size(max = 400, message = "sobre can't have more than 400 caracters") String sobre
) {
}
