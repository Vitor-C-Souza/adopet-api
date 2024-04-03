package br.com.alura.adopet.api.dto;

public record AbrigoCadastrarDto(
        Long id,
        String nome,
        String telefone,
        String email
) {
}
