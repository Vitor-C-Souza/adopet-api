package me.vitorcsouza.adopet_api.domain.service;

import me.vitorcsouza.adopet_api.domain.dto.*;
import me.vitorcsouza.adopet_api.domain.model.Abrigo;
import me.vitorcsouza.adopet_api.domain.model.Adocao;
import me.vitorcsouza.adopet_api.domain.model.Pet;
import me.vitorcsouza.adopet_api.domain.model.Tutor;
import me.vitorcsouza.adopet_api.domain.repository.AbrigoRepository;
import me.vitorcsouza.adopet_api.domain.repository.AdocaoRepository;
import me.vitorcsouza.adopet_api.domain.repository.PetRepository;
import me.vitorcsouza.adopet_api.domain.repository.TutorRepository;
import me.vitorcsouza.adopet_api.domain.service.conversor.ConvertAdocao;
import me.vitorcsouza.adopet_api.domain.service.impl.AdocaoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {
    @InjectMocks
    private AdocaoServiceImpl service;

    @Mock
    private AdocaoRepository repository;
    @Mock
    private PetRepository petRepository;
    @Mock
    private TutorRepository tutorRepository;
    @Mock
    private AbrigoRepository abrigoRepository;

    @Mock
    private ConvertAdocao convert;

    private Adocao adocao;
    private AdocaoDtoReq dtoReq;
    private AdocaoDtoRes dtoRes;
    private TutorDtoReq tutorDtoReq;
    private Tutor tutor;
    private PetDtoReq petDtoReq;
    private Pet pet;
    private Abrigo abrigo;


    @Captor
    private ArgumentCaptor<Adocao> adocaoArgumentCaptor;

    @BeforeEach
    void setUp(){
        dtoReq = new AdocaoDtoReq(
                4L,
                2L,
                "14/12/1997"
        );
        petDtoReq = new PetDtoReq(
                "Mimi",
                "Gata siamês de pelagem curta, dócil e silenciosa",
                "5 anos",
                "Rua dos Gatos, 202, Vila Felina",
                "http://example.com/imagens/mimi.jpg",
                4L,
                false
        );
        AbrigoDtoReq abrigoDtoReq = new AbrigoDtoReq(
                "Refúgio dos Anjos",
                "refugio@anjos.com.br",
                "(19) 98765-4321"
        );
        abrigo = new Abrigo(abrigoDtoReq);
        given(abrigoRepository.findById(petDtoReq.abrigos_id())).willReturn(Optional.of(abrigo));

        pet = new Pet(petDtoReq, abrigoRepository);
        given(petRepository.findByIdAvailable(dtoReq.animal())).willReturn(Optional.of(pet));

        tutorDtoReq = new TutorDtoReq(
                "link_para_foto_de_perfil3.jpg",
                "Fernanda Souza Lima",
                "+55 41 99999-8888",
                "Curitiba-PR",
                "Especialista em automação de processos com Python, Fernanda tem experiência em análise de dados e automação de fluxos repetitivos. Ela busca aplicar seus conhecimentos em grandes projetos, com foco na melhoria da produtividade e eficiência operacional."
        );
        tutor = new Tutor(tutorDtoReq);
        given(tutorRepository.findById(dtoReq.tutor())).willReturn(Optional.of(tutor));

        adocao = new Adocao(dtoReq, petRepository, tutorRepository);
        dtoRes = new AdocaoDtoRes(adocao);

        lenient().when(convert.toDto(adocao)).thenReturn(dtoRes);
        lenient().when(convert.toModel(dtoReq, petRepository, tutorRepository)).thenReturn(adocao);

        given(petRepository.findById(adocao.getAnimal().getId())).willReturn(Optional.ofNullable(pet));
    }

    @Test
    void deveCriarUmaAdocao(){
        //ARRANGE

        //ACT
        AdocaoDtoRes adocaoDtoRes = service.create(dtoReq);

        //ASSERT
        then(repository).should().save(adocaoArgumentCaptor.capture());
        Adocao value = adocaoArgumentCaptor.getValue();

        assertEquals(adocaoDtoRes.id(), value.getId());
    }

    @Test
    void deveApagarUmaAdocao(){
        //ARRANGE
        Long id = 1L;
        given(repository.findById(id)).willReturn(Optional.ofNullable(adocao));
        //ACT
        service.delete(id);

        //ASSERT
        verify(repository, times(1)).deleteById(id);
    }
}