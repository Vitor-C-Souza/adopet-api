package me.vitorcsouza.adopet_api.domain.service.impl;

import me.vitorcsouza.adopet_api.domain.dto.CadastroDto;
import me.vitorcsouza.adopet_api.domain.model.Login;
import me.vitorcsouza.adopet_api.domain.repository.LoginRespository;
import me.vitorcsouza.adopet_api.domain.service.LoginService;
import me.vitorcsouza.adopet_api.domain.service.conversor.ConvertLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService, UserDetailsService {
    @Autowired
    private ConvertLogin convert;
    @Autowired
    private LoginRespository respository;

    @Override
    public CadastroDto cadastroAbrigo(CadastroDto dto, String senha) {
        Login cadastro = convert.toModel(dto, senha, "abrigo");
        respository.save(cadastro);
        return convert.toDto(cadastro, dto.senha());
    }

    @Override
    public CadastroDto cadastroTutor(CadastroDto dto, String senha) {
        Login cadastro = convert.toModel(dto, senha, "tutor");
        respository.save(cadastro);
        return convert.toDto(cadastro, dto.senha());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return respository.findByUsuario(username);
    }
}
