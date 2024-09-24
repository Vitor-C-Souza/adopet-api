package me.vitorcsouza.adopet_api.controller;

import me.vitorcsouza.adopet_api.domain.dto.CadastroDto;
import me.vitorcsouza.adopet_api.domain.dto.TutorDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.TutorDtoRes;
import me.vitorcsouza.adopet_api.domain.model.Login;
import me.vitorcsouza.adopet_api.domain.model.Tutor;
import me.vitorcsouza.adopet_api.domain.service.TutorService;
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
class TutorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TutorService service;
    @MockBean
    private TokenService tokenService;

    private TutorDtoRes dtoRes;
    private TutorDtoReq dtoReq;

    private Long id;
    String json;
    private String token;

    private PageRequest paginacao;

    private Page<Tutor> tutorPage;

    @BeforeEach
    void setUp() {
        id = 1L;
        CadastroDto cadastroDto = new CadastroDto(id, "vitor", "vitor@example.com", "1234");
        Login login = new Login(cadastroDto, "1234", "ABRIGO");
        token = tokenService.GerarToken(login);
        when(tokenService.GerarToken(login)).thenReturn(token);

        when(tokenService.getPerfilFromToken(any(String.class))).thenReturn("ABRIGO");
        when(tokenService.getSubject(anyString())).thenReturn(login.getUsuario());
        json = """
                {
                  "fotoDePerfil": "link_para_foto_de_perfil3.jpg",
                  "nome": "Fernanda Souza Lima",
                  "telefone": "+55 41 99999-8888",
                  "cidade": "Curitiba-PR",
                  "sobre": "Especialista em automação de processos com Python, Fernanda tem experiência em análise de dados e automação de fluxos repetitivos. Ela busca aplicar seus conhecimentos em grandes projetos, com foco na melhoria da produtividade e eficiência operacional."
                }
                """;
        dtoReq = new TutorDtoReq(
                "link_para_foto_de_perfil3.jpg",
                "Fernanda Souza Lima",
                "+55 41 99999-8888",
                "Curitiba-PR",
                "Especialista em automação de processos com Python, Fernanda tem experiência em análise de dados e automação de fluxos repetitivos. Ela busca aplicar seus conhecimentos em grandes projetos, com foco na melhoria da produtividade e eficiência operacional."
        );

        Tutor tutor1 = new Tutor(dtoReq);
        Tutor tutor2 = new Tutor(dtoReq);


        dtoRes = new TutorDtoRes(tutor1);

        List<Tutor> tutores = Arrays.asList(tutor1, tutor2);
        paginacao = PageRequest.of(0, 15);

        tutorPage = new PageImpl<>(tutores, paginacao, tutores.size());
    }

    @Test
    void deveRetornarCodigo201ParaSalvarUmTutor() throws Exception {
        //ARRANGE
        when(service.create(any(TutorDtoReq.class))).thenReturn(dtoRes);

        //ACT
        var response = mockMvc.perform(post("/tutor")
                .header("Authorization", "Bearer " + token)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(201, response.getStatus());
    }

    @Test
    void deveRetornarCodigo200ParaEncontrarUmTutor() throws Exception {
        //ARRANGE
        when(service.findById(id)).thenReturn(dtoRes);

        //ACT
        var response = mockMvc.perform(get("/tutor/" + id)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarCodigo200ParaUmaPaginaDeTutores() throws Exception {
        //ARRANGE
        given(service.findAll(paginacao)).willReturn(tutorPage.map(TutorDtoRes::new));

        //ACT
        var response = mockMvc.perform(get("/tutor")
                .header("Authorization", "Bearer " + token)
                .param("page", "0")
                .param("size", "15")
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarCodigo200ParaUmaAtualizacaoDeUmTutor() throws Exception {
        //ARRANGE
        when(service.update(dtoReq, id)).thenReturn(dtoRes);

        //ACT
        var response = mockMvc.perform(put("/tutor/" + id)
                .header("Authorization", "Bearer " + token)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarCodigo204ParaDeletarUmTutor() throws Exception {
        //ARRANGE

        //ACT
        var response = mockMvc.perform(delete("/tutor/" + id)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(204, response.getStatus());
    }
}