package me.vitorcsouza.adopet_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import me.vitorcsouza.adopet_api.domain.dto.CadastroDto;
import me.vitorcsouza.adopet_api.domain.dto.LoginDto;
import me.vitorcsouza.adopet_api.domain.dto.TokenJWT;
import me.vitorcsouza.adopet_api.domain.model.Login;
import me.vitorcsouza.adopet_api.domain.service.LoginService;
import me.vitorcsouza.adopet_api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
public class LoginController {
    @Autowired
    private LoginService service;

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;

    @PostMapping("abrigo/cadastro")
    @Operation(summary = "Cadastra um perfil do tipo abrigo", tags = {"Cadastro"})
    public ResponseEntity<CadastroDto> cadastrarAbrigo(@RequestBody @Valid CadastroDto dto, UriComponentsBuilder uri) {
        String senhaEncriptada = BCrypt.hashpw(dto.senha(), BCrypt.gensalt());
        CadastroDto cadastro = service.cadastroAbrigo(dto, senhaEncriptada);

        URI address = uri.path("abrigo/cadastro/{id}").buildAndExpand(dto.id()).toUri();

        return ResponseEntity.created(address).body(cadastro);
    }

    @PostMapping("tutor/cadastro")
    @Operation(summary = "Cadastra um perfil do tipo tutor", tags = {"Cadastro"})
    public ResponseEntity<CadastroDto> cadastrarTutor(@RequestBody @Valid CadastroDto dto, UriComponentsBuilder uri) {
        String senhaEncriptada = BCrypt.hashpw(dto.senha(), BCrypt.gensalt());
        CadastroDto cadastro = service.cadastroTutor(dto, senhaEncriptada);

        URI address = uri.path("tutor/cadastro/{id}").buildAndExpand(dto.id()).toUri();

        return ResponseEntity.created(address).body(cadastro);
    }

    @PostMapping("/logar")
    @Operation(summary = "Efetua o login no sistema", tags = {"Login"})
    public ResponseEntity<TokenJWT> logar(@RequestBody @Valid LoginDto dto){

        var autheticationToken = new UsernamePasswordAuthenticationToken(dto.usuario(), dto.senha());
        var authentication = manager.authenticate(autheticationToken);

        var tokenJWT =  tokenService.GerarToken((Login) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenJWT(tokenJWT));
    }
}
