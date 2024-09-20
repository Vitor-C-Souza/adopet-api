package me.vitorcsouza.adopet_api.domain.service;

import me.vitorcsouza.adopet_api.domain.dto.AbrigoDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.PetDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.PetDtoRes;
import me.vitorcsouza.adopet_api.domain.model.Abrigo;
import me.vitorcsouza.adopet_api.domain.model.Pet;
import me.vitorcsouza.adopet_api.domain.repository.AbrigoRepository;
import me.vitorcsouza.adopet_api.domain.repository.PetRepository;
import me.vitorcsouza.adopet_api.domain.service.conversor.ConvertPet;
import me.vitorcsouza.adopet_api.domain.service.impl.PetServiceImpl;
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
class PetServiceTest {
    @InjectMocks
    private PetServiceImpl service;

    @Mock
    private PetRepository repository;
    @Mock
    private AbrigoRepository abrigoRepository;

    @Mock
    private ConvertPet convert;

    @Captor
    private ArgumentCaptor<Pet> petArgumentCaptor;

    private Pet pet1;
    private Pet pet2;
    private Abrigo abrigo;
    private PetDtoRes dtoRes;
    private PetDtoReq dtoReq;
    Long id;
    Pageable paginacao;
    Page<Pet> petPage;


    @BeforeEach
    void setUp() {
        id = 1L;
        dtoReq = new PetDtoReq(
                "Mimi",
                "Gata siamês de pelagem curta, dócil e silenciosa",
                "5 anos",
                "Rua dos Gatos, 202, Vila Felina",
                "http://example.com/imagens/mimi.jpg",
                id,
                false
        );
        AbrigoDtoReq abrigoDtoReq = new AbrigoDtoReq(
                "Refúgio dos Anjos",
                "refugio@anjos.com.br",
                "(19) 98765-4321"
        );
        abrigo = new Abrigo(abrigoDtoReq);
        given(abrigoRepository.findById(dtoReq.abrigos_id())).willReturn(Optional.of(abrigo));

        pet1 = new Pet(dtoReq, abrigoRepository);
        pet2 = new Pet(dtoReq, abrigoRepository);
        dtoRes = new PetDtoRes(pet1);

        lenient().when(convert.toDto(pet1)).thenReturn(dtoRes);
        lenient().when(convert.toModel(dtoReq, abrigoRepository)).thenReturn(pet1);


        List<Pet> petList = Arrays.asList(pet1, pet2);
        paginacao = PageRequest.of(0, 10);
        petPage = new PageImpl<>(petList, paginacao, petList.size());
    }

    @Test
    void deveCriarUmNovoPet() {
        //ARRANGE

        //ACT
        PetDtoRes petDtoRes = service.create(dtoReq);

        //ASSERT
        then(repository).should().save(petArgumentCaptor.capture());
        Pet captorValue = petArgumentCaptor.getValue();

        assertEquals(petDtoRes.id(), captorValue.getId());
        assertEquals(petDtoRes.nome(), captorValue.getNome());
        assertEquals(petDtoRes.descricao(), captorValue.getDescricao());
        assertEquals(petDtoRes.adotado(), captorValue.isAdotado());
        assertEquals(petDtoRes.idade(), captorValue.getIdade());assertEquals(petDtoRes.id(), captorValue.getId());
        assertEquals(petDtoRes.nome(), captorValue.getNome());
        assertEquals(petDtoRes.descricao(), captorValue.getDescricao());
        assertEquals(petDtoRes.adotado(), captorValue.isAdotado());
        assertEquals(petDtoRes.idade(), captorValue.getIdade());
        assertEquals(petDtoRes.endereco(), captorValue.getEndereco());
        assertEquals(petDtoRes.imagem(), captorValue.getImagem());
        assertEquals(petDtoRes.abrigo().id(), captorValue.getAbrigo().getId());
        assertEquals(petDtoRes.abrigo().nome(), captorValue.getAbrigo().getNome());
        assertEquals(petDtoRes.abrigo().email(), captorValue.getAbrigo().getEmail());
        assertEquals(petDtoRes.abrigo().telefone(), captorValue.getAbrigo().getTelefone());
        assertEquals(petDtoRes.endereco(), captorValue.getEndereco());
        assertEquals(petDtoRes.imagem(), captorValue.getImagem());
        assertEquals(petDtoRes.abrigo().id(), captorValue.getAbrigo().getId());
        assertEquals(petDtoRes.abrigo().nome(), captorValue.getAbrigo().getNome());
        assertEquals(petDtoRes.abrigo().email(), captorValue.getAbrigo().getEmail());
        assertEquals(petDtoRes.abrigo().telefone(), captorValue.getAbrigo().getTelefone());
    }

    @Test
    void deveRetornarUmPet() {
        //ARRANGE
        given(repository.findByIdAvailable(id)).willReturn(Optional.of(pet1));

        //ACT
        PetDtoRes byId = service.findById(id);

        //ASSERT

        assertEquals(byId.id(), pet1.getId());
        assertEquals(byId.nome(), pet1.getNome());
        assertEquals(byId.descricao(), pet1.getDescricao());
        assertEquals(byId.adotado(), pet1.isAdotado());
        assertEquals(byId.idade(), pet1.getIdade());
        assertEquals(byId.endereco(), pet1.getEndereco());
        assertEquals(byId.imagem(), pet1.getImagem());
        assertEquals(byId.abrigo().id(), pet1.getAbrigo().getId());
        assertEquals(byId.abrigo().nome(), pet1.getAbrigo().getNome());
        assertEquals(byId.abrigo().email(), pet1.getAbrigo().getEmail());
        assertEquals(byId.abrigo().telefone(), pet1.getAbrigo().getTelefone());
    }

    @Test
    void deveRetornarUmaPaginaDePet(){
        //ARRANGE
        given(repository.findAllAvailable(paginacao)).willReturn(petPage);

        //ACT
        Page<PetDtoRes> petDtoResPage = service.findAll(paginacao);

        //ASSERT
        assertEquals(petDtoResPage, petPage.map(PetDtoRes::new));
    }

    @Test
    void deveRetornarUmPetAtualizado(){
        //ARRANGE
        given(repository.getReferenceById(id)).willReturn(pet1);

        //ACT
        PetDtoRes updated = service.update(dtoReq, id);

        //ASSERT
        assertEquals(updated.id(), pet1.getId());
        assertEquals(updated.nome(), pet1.getNome());
        assertEquals(updated.descricao(), pet1.getDescricao());
        assertEquals(updated.adotado(), pet1.isAdotado());
        assertEquals(updated.idade(), pet1.getIdade());
        assertEquals(updated.endereco(), pet1.getEndereco());
        assertEquals(updated.imagem(), pet1.getImagem());
        assertEquals(updated.abrigo().id(), pet1.getAbrigo().getId());
        assertEquals(updated.abrigo().nome(), pet1.getAbrigo().getNome());
        assertEquals(updated.abrigo().email(), pet1.getAbrigo().getEmail());
        assertEquals(updated.abrigo().telefone(), pet1.getAbrigo().getTelefone());
    }

    @Test
    void deveApagarUmPet() {
        //ARRANGE

        //ACT
        service.delete(id);

        //ASSERT
        verify(repository, times(1)).deleteById(id);
    }
}