package me.vitorcsouza.adopet_api.controller;

import me.vitorcsouza.adopet_api.domain.dto.*;
import me.vitorcsouza.adopet_api.domain.model.Abrigo;
import me.vitorcsouza.adopet_api.domain.model.Pet;
import me.vitorcsouza.adopet_api.domain.repository.AbrigoRepository;
import me.vitorcsouza.adopet_api.domain.service.PetService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetService service;

    @MockBean
    private AbrigoRepository abrigoRepository;

    private PetDtoRes dtoRes;
    private PetDtoReq dtoReq;

    private Long id;
    String json;

    private PageRequest paginacao;
    private Page<Pet> petPage;

    @BeforeEach
    void setUp(){
        Abrigo abrigo = new Abrigo(new AbrigoDtoReq(
                "Refúgio dos Anjos",
                "refugio@anjos.com.br",
                "(19) 98765-4321"
        ));

        when(abrigoRepository.findById(anyLong())).thenReturn(Optional.of(abrigo));
        json = """
                {
                  "nome": "Mimi",
                  "descricao": "Gata siamês de pelagem curta, dócil e silenciosa",
                  "idade": "5 anos",
                  "endereco": "Rua dos Gatos, 202, Vila Felina",
                  "imagem": "http://example.com/imagens/mimi.jpg",
                  "abrigos_id": 4
                }
                """;
        dtoReq = new PetDtoReq(
                "Mimi",
                "Gata siamês de pelagem curta, dócil e silenciosa",
                "5 anos",
                "Rua dos Gatos, 202, Vila Felina",
                "http://example.com/imagens/mimi.jpg",
                4L,
                false
        );

        Pet pet1 = new Pet(dtoReq, abrigoRepository);
        Pet pet2 = new Pet(dtoReq, abrigoRepository);

        id = 1L;
        dtoRes = new PetDtoRes(pet1);

        List<Pet> petList = Arrays.asList(pet1, pet2);
        paginacao = PageRequest.of(0, 15);
        petPage = new PageImpl<>(petList, paginacao, petList.size());
    }

    @Test
    void deveRetornarCodigo201ParaSalvarUmPet() throws Exception {
        //ARRANGE
        when(service.create(any(PetDtoReq.class))).thenReturn(dtoRes);

        //ACT
        var response = mockMvc.perform(post("/pet")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(201, response.getStatus());
    }

    @Test
    void deveRetornarCodigo200ParaEncontrarUmPet() throws Exception {
        //ARRANGE
        when(service.findById(id)).thenReturn(dtoRes);

        //ACT
        var response = mockMvc.perform(get("/pet/" + id)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarCodigo200ParaUmaPaginaDePets() throws Exception {
        //ARRANGE
        given(service.findAll(paginacao)).willReturn(petPage.map(PetDtoRes::new));

        //ACT
        var response = mockMvc.perform(get("/pet")
                .param("page", "0")
                .param("size", "15")
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarCodigo200ParaUmaAtualizacaoDeUmPet() throws Exception {
        //ARRANGE
        when(service.update(dtoReq, id)).thenReturn(dtoRes);

        //ACT
        var response = mockMvc.perform(put("/pet/" + id)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarCodigo204ParaDeletarUmPet() throws Exception {
        //ARRANGE

        //ACT
        var response = mockMvc.perform(delete("/pet/" + id)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(204, response.getStatus());
    }

}