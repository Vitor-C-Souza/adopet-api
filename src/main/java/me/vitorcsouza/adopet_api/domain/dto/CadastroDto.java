package me.vitorcsouza.adopet_api.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import me.vitorcsouza.adopet_api.domain.model.Login;

public record CadastroDto(
        Long id,
        @NotBlank @NotNull String usuario,
        @NotBlank @NotNull String email,
        @NotBlank @NotNull String senha
) {
    public CadastroDto(Login cadastro, String senha) {
        this(
                cadastro.getId(),
                cadastro.getUsuario(),
                cadastro.getEmail(),
                senha
        );
    }
}
