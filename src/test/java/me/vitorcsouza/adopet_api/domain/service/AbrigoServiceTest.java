package me.vitorcsouza.adopet_api.domain.service;

import me.vitorcsouza.adopet_api.domain.dto.AbrigoDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.AbrigoDtoRes;
import me.vitorcsouza.adopet_api.domain.model.Abrigo;
import me.vitorcsouza.adopet_api.domain.repository.AbrigoRepository;
import me.vitorcsouza.adopet_api.domain.service.conversor.ConvertAbrigo;
import me.vitorcsouza.adopet_api.domain.service.impl.AbrigoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {
    @InjectMocks
    private AbrigoServiceImpl service;

    @Mock
    private AbrigoRepository repository;

    @Mock
    private ConvertAbrigo convert;

    @Captor
    private ArgumentCaptor<Abrigo> abrigoArgumentCaptor;

    private Abrigo abrigo1;
    private Abrigo abrigo2;
    private AbrigoDtoRes dtoRes;
    private AbrigoDtoReq dtoReq;
    Long id;
    Pageable paginacao;
    Page<Abrigo> abrigoPage;

    @BeforeEach
    void setUp() {
        dtoReq = new AbrigoDtoReq(
                "Refúgio dos Anjos",
                "refugio@anjos.com.br",
                "(19) 98765-4321"
        );

        abrigo1 = new Abrigo(dtoReq);
        abrigo2 = new Abrigo(dtoReq);
        dtoRes = new AbrigoDtoRes(abrigo1);

        lenient().when(convert.toDto(abrigo1)).thenReturn(dtoRes);
        lenient().when(convert.toModel(dtoReq)).thenReturn(abrigo1);
        id = 1L;

        List<Abrigo> abrigoList = Arrays.asList(abrigo1, abrigo2);
        paginacao = PageRequest.of(0, 10);
        abrigoPage = new PageImpl<>(abrigoList, paginacao, abrigoList.size());
    }

    @Test
    void deveCriarUmAbrigoNovo() {
        //ARRANGE

        //ACT
        AbrigoDtoRes abrigoDtoRes = service.create(dtoReq);

        //ASSERT
        then(repository).should().save(abrigoArgumentCaptor.capture());
        Abrigo abrigoSalvo = abrigoArgumentCaptor.getValue();

        assertEquals(abrigoDtoRes.id(), abrigoSalvo.getId());
        assertEquals(abrigoDtoRes.nome(), abrigoSalvo.getNome());
        assertEquals(abrigoDtoRes.email(), abrigoSalvo.getEmail());
        assertEquals(abrigoDtoRes.telefone(), abrigoSalvo.getTelefone());
    }

    @Test
    void deveRetornarUmAbrigo() {
        //ARRANGE
        given(repository.findById(id)).willReturn(Optional.of(abrigo1));

        //ACT
        AbrigoDtoRes byId = service.findById(id);

        //ASSERT
        assertEquals(abrigo1.getId(), byId.id());
        assertEquals(abrigo1.getEmail(), byId.email());
        assertEquals(abrigo1.getNome(), byId.nome());
        assertEquals(abrigo1.getTelefone(), byId.telefone());
    }

    @Test
    void deveRetornarUmaPaginaDeAbrigo() {
        //ARRANGE
        given(repository.findAll(paginacao)).willReturn(abrigoPage);

        //ACT
        Page<AbrigoDtoRes> abrigoDtoResPage = service.findAll(paginacao);

        //ASSERT
        assertEquals(abrigoPage.map(AbrigoDtoRes::new), abrigoDtoResPage);
    }

    @Test
    void deveRetornarUmAbrigoAtualizado() {
        //ARRANGE
        given(repository.getReferenceById(id)).willReturn(abrigo1);

        //ACT
        AbrigoDtoRes updated = service.update(dtoReq, id);

        //ASSERT
        assertEquals(abrigo1.getId(), updated.id());
        assertEquals(abrigo1.getNome(), updated.nome());
        assertEquals(abrigo1.getEmail(), updated.email());
        assertEquals(abrigo1.getTelefone(), updated.telefone());
    }

    @Test
    void deveApagarUmAbrigo() {
        //ARRANGE

        //ACT
        service.delete(id);

        //ASSERT
        verify(repository, times(1)).deleteById(id);
    }
}