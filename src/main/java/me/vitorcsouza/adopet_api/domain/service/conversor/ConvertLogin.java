package me.vitorcsouza.adopet_api.domain.service.conversor;

import me.vitorcsouza.adopet_api.domain.dto.CadastroDto;
import me.vitorcsouza.adopet_api.domain.model.Login;
import org.springframework.stereotype.Component;

@Component
public class ConvertLogin {
    public Login toModel(CadastroDto dto, String senha, String tipo) {
        return new Login(dto, senha, tipo);
    }

    public CadastroDto toDto(Login cadastro, String senha) {
        return new CadastroDto(cadastro, senha);
    }
}
