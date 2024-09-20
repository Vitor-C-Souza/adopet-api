package me.vitorcsouza.adopet_api.domain.service;

import me.vitorcsouza.adopet_api.domain.dto.TutorDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.TutorDtoRes;
import me.vitorcsouza.adopet_api.domain.model.Tutor;
import me.vitorcsouza.adopet_api.domain.repository.TutorRepository;
import me.vitorcsouza.adopet_api.domain.service.conversor.ConvertTutor;
import me.vitorcsouza.adopet_api.domain.service.impl.TutorServiceImpl;
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
class TutorServiceTest {
    @InjectMocks
    private TutorServiceImpl service;

    @Mock
    private TutorRepository repository;
    @Mock
    private ConvertTutor convertTutor;

    @Captor
    private ArgumentCaptor<Tutor> tutorArgumentCaptor;

    private Tutor tutor1;
    private Tutor tutor2;
    private TutorDtoReq dtoReq;
    private TutorDtoRes dtoRes;
    Long id;
    Pageable paginacao;
    Page<Tutor> tutorPage;

    @BeforeEach
    void setUp() {
        dtoReq = new TutorDtoReq(
                "link_para_foto_de_perfil3.jpg",
                "Fernanda Souza Lima",
                "+55 41 99999-8888",
                "Curitiba-PR",
                "Especialista em automação de processos com Python, Fernanda tem experiência em análise de dados e automação de fluxos repetitivos. Ela busca aplicar seus conhecimentos em grandes projetos, com foco na melhoria da produtividade e eficiência operacional."
        );

        tutor1 = new Tutor(dtoReq);
        tutor2 = new Tutor(dtoReq);
        dtoRes = new TutorDtoRes(tutor1);

        lenient().when(convertTutor.toDto(tutor1)).thenReturn(dtoRes);
        lenient().when(convertTutor.toModel(dtoReq)).thenReturn(tutor1);
        id = 1L;

        List<Tutor> tutorList = Arrays.asList(tutor1, tutor2);
        paginacao = PageRequest.of(0, 10);
        tutorPage = new PageImpl<>(tutorList, paginacao, tutorList.size());
    }

    @Test
    void deveCriarUmNovoTutor() {
        //ARRANGE

        //ACT
        TutorDtoRes tutorDtoRes = service.create(dtoReq);

        //ASSERT
        then(repository).should().save(tutorArgumentCaptor.capture());
        Tutor tutor = tutorArgumentCaptor.getValue();

        assertEquals(tutorDtoRes.id(), tutor.getId());
        assertEquals(tutorDtoRes.fotoDePerfil(), tutor.getFotoDePerfil());
        assertEquals(tutorDtoRes.nome(), tutor.getNome());
        assertEquals(tutorDtoRes.telefone(), tutor.getTelefone());
        assertEquals(tutorDtoRes.cidade(), tutor.getCidade());
        assertEquals(tutorDtoRes.sobre(), tutor.getSobre());
    }

    @Test
    void deveRetornarUmTutor() {
        //ARRANGE
        given(repository.findById(id)).willReturn(Optional.of(tutor1));

        //ACT
        TutorDtoRes byId = service.findById(id);

        //ASSERT

        assertEquals(byId.id(), tutor1.getId());
        assertEquals(byId.fotoDePerfil(), tutor1.getFotoDePerfil());
        assertEquals(byId.nome(), tutor1.getNome());
        assertEquals(byId.telefone(), tutor1.getTelefone());
        assertEquals(byId.cidade(), tutor1.getCidade());
        assertEquals(byId.sobre(), tutor1.getSobre());
    }

    @Test
    void deveRetornarUmaPaginaDeTutor() {
        //ARRANGE
        given(repository.findAll(paginacao)).willReturn(tutorPage);

        //ACT
        Page<TutorDtoRes> tutorDtoResPage = service.findAll(paginacao);

        //ASSERT
        assertEquals(tutorPage.map(TutorDtoRes::new), tutorDtoResPage);
    }

    @Test
    void deveRetornarUmTutorAtualizado() {
        //ARRANGE
        given(repository.getReferenceById(id)).willReturn(tutor1);

        //ACT
        TutorDtoRes updated = service.update(dtoReq, id);

        //ASSERT

        assertEquals(updated.id(), tutor1.getId());
        assertEquals(updated.fotoDePerfil(), tutor1.getFotoDePerfil());
        assertEquals(updated.nome(), tutor1.getNome());
        assertEquals(updated.telefone(), tutor1.getTelefone());
        assertEquals(updated.cidade(), tutor1.getCidade());
        assertEquals(updated.sobre(), tutor1.getSobre());
    }

    @Test
    void deveApagarUmTutor() {
        //ARRANGE

        //ACT
        service.delete(id);

        //ASSERT
        verify(repository, times(1)).deleteById(id);
    }

}