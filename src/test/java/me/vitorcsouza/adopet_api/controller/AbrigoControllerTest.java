package me.vitorcsouza.adopet_api.controller;

import me.vitorcsouza.adopet_api.domain.dto.AbrigoDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.AbrigoDtoRes;
import me.vitorcsouza.adopet_api.domain.dto.CadastroDto;
import me.vitorcsouza.adopet_api.domain.model.Abrigo;
import me.vitorcsouza.adopet_api.domain.model.Login;
import me.vitorcsouza.adopet_api.domain.service.AbrigoService;
import me.vitorcsouza.adopet_api.infra.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class AbrigoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AbrigoService service;
    @MockBean
    private TokenService tokenService;

    private AbrigoDtoRes dtoRes;
    private AbrigoDtoReq dtoReq;

    private Long id;
    private String json;
    private String token;


    private PageRequest paginacao;
    private Page<Abrigo> abrigoPage;


    @BeforeEach
    void setUp(){
        id = 1L;
        CadastroDto cadastroDto = new CadastroDto(id, "vitor", "vitor@example.com", "1234");
        Login login = new Login(cadastroDto, "1234", "ABRIGO");
        token = tokenService.GerarToken(login);
        when(tokenService.GerarToken(login)).thenReturn(token);

        when(tokenService.getPerfilFromToken(any(String.class))).thenReturn("ABRIGO");
        when(tokenService.getSubject(anyString())).thenReturn(login.getUsuario());
        json= """
                {
                  "nome": "Refúgio dos Anjos",
                  "email": "refugio@anjos.com.br",
                  "telefone": "(19) 98765-4321"
                }
                """;
        dtoReq = new AbrigoDtoReq(
                "Refúgio dos Anjos",
                "refugio@anjos.com.br",
                "(19) 98765-4321"
        );

        Abrigo abrigo1 = new Abrigo(dtoReq);
        Abrigo abrigo2 = new Abrigo(dtoReq);


        dtoRes = new AbrigoDtoRes(abrigo1);
        List<Abrigo> abrigoList = Arrays.asList(abrigo1, abrigo2);
        paginacao = PageRequest.of(0, 15);
        abrigoPage = new PageImpl<>(abrigoList, paginacao, abrigoList.size());
    }

    @Test
    void deveRetornarCodigo201ParaSalvarUmAbrigo() throws Exception {
        //ARRANGE
        when(service.create(any(AbrigoDtoReq.class))).thenReturn(dtoRes);

        //ACT
        var response = mockMvc.perform(post("/abrigo")
                .header("Authorization", "Bearer " + token)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(201, response.getStatus());
    }

    @Test
    void deveRetornarCodigo200ParaEncontrarUmAbrigo() throws Exception {
        //ARRANGE
        when(service.findById(id)).thenReturn(dtoRes);

        //ACT
        var response = mockMvc.perform(get("/abrigo/" + id)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarCodigo200ParaUmaPaginaDeAbrigos() throws Exception {
        //ARRANGE
        given(service.findAll(paginacao)).willReturn(abrigoPage.map(AbrigoDtoRes::new));

        //ACT
        var response = mockMvc.perform(get("/abrigo")
                .header("Authorization", "Bearer " + token)
                .param("page", "0")
                .param("size", "15")
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarCodigo200ParaUmaAtualizacaoDeUmAbrigo() throws Exception {
        //ARRANGE
        when(service.update(dtoReq, id)).thenReturn(dtoRes);

        //ACT
        var response = mockMvc.perform(put("/abrigo/" + id)
                .header("Authorization", "Bearer " + token)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarCodigo204ParaDeletarUmAbrigo() throws Exception {
        //ARRANGE

        //ACT
        var response = mockMvc.perform(delete("/abrigo/" + id)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(204, response.getStatus());
    }
}