package me.vitorcsouza.adopet_api.controller;

import me.vitorcsouza.adopet_api.domain.dto.*;
import me.vitorcsouza.adopet_api.domain.model.Abrigo;
import me.vitorcsouza.adopet_api.domain.model.Adocao;
import me.vitorcsouza.adopet_api.domain.model.Pet;
import me.vitorcsouza.adopet_api.domain.model.Tutor;
import me.vitorcsouza.adopet_api.domain.repository.AbrigoRepository;
import me.vitorcsouza.adopet_api.domain.repository.PetRepository;
import me.vitorcsouza.adopet_api.domain.repository.TutorRepository;
import me.vitorcsouza.adopet_api.domain.service.AdocaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class AdocaoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdocaoService service;
    @MockBean
    private PetRepository petRepository;
    @MockBean
    private TutorRepository tutorRepository;
    @MockBean
    private AbrigoRepository abrigoRepository;

    private AdocaoDtoRes dtoRes;

    String json;


    @BeforeEach
    void setUp(){

        Abrigo abrigo = new Abrigo(new AbrigoDtoReq(
                "Refúgio dos Anjos",
                "refugio@anjos.com.br",
                "(19) 98765-4321"
        ));

        when(abrigoRepository.findById(anyLong())).thenReturn(Optional.of(abrigo));

        Pet pet = new Pet(new PetDtoReq(
                "Mimi",
                "Gata siamês de pelagem curta, dócil e silenciosa",
                "5 anos",
                "Rua dos Gatos, 202, Vila Felina",
                "http://example.com/imagens/mimi.jpg",
                4L,
                false
        ), abrigoRepository);

        when(petRepository.findById(anyLong())).thenReturn(Optional.of(pet));

        Tutor tutor = new Tutor(new TutorDtoReq(
                "link_para_foto_de_perfil3.jpg",
                "Fernanda Souza Lima",
                "+55 41 99999-8888",
                "Curitiba-PR",
                "Especialista em automação de processos com Python, Fernanda tem experiência em análise de dados e automação de fluxos repetitivos. Ela busca aplicar seus conhecimentos em grandes projetos, com foco na melhoria da produtividade e eficiência operacional."
        ));

        when(tutorRepository.findById(anyLong())).thenReturn(Optional.of(tutor));

        json = """
                {
                    "animal": 3,
                    "tutor": 2,
                    "data": "14/12/1997"
                }
                """;

        AdocaoDtoReq dtoReq = new AdocaoDtoReq(
                3L,
                2L,
                "14/12/1997"
        );

        Adocao adocao = new Adocao(dtoReq, petRepository, tutorRepository);

        dtoRes = new AdocaoDtoRes(adocao);
    }

    @Test
    void deveRetornarCodigo201ParaSalvarUmAbrigo() throws Exception {
        //ARRANGE
        when(service.create(any(AdocaoDtoReq.class))).thenReturn(dtoRes);

        //ACT
        var response = mockMvc.perform(post("/adocao")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(201, response.getStatus());
    }

    @Test
    void deveRetornarCodigo204ParaDeletarUmTutor() throws Exception {
        //ARRANGE

        //ACT
        var response = mockMvc.perform(delete("/adocao/" + anyLong())
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(204, response.getStatus());
    }

}