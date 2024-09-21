package me.vitorcsouza.adopet_api.domain.service.impl;

import jakarta.transaction.Transactional;
import me.vitorcsouza.adopet_api.domain.dto.AdocaoDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.AdocaoDtoRes;
import me.vitorcsouza.adopet_api.domain.model.Adocao;
import me.vitorcsouza.adopet_api.domain.model.Pet;
import me.vitorcsouza.adopet_api.domain.repository.AdocaoRepository;
import me.vitorcsouza.adopet_api.domain.repository.PetRepository;
import me.vitorcsouza.adopet_api.domain.repository.TutorRepository;
import me.vitorcsouza.adopet_api.domain.service.AdocaoService;
import me.vitorcsouza.adopet_api.domain.service.conversor.ConvertAdocao;
import me.vitorcsouza.adopet_api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AdocaoServiceImpl implements AdocaoService {
    @Autowired
    private AdocaoRepository repository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private ConvertAdocao convert;
    @Autowired
    private TokenService tokenService;

    @Override
    @Transactional
    public AdocaoDtoRes create(AdocaoDtoReq dto) {
        Adocao adocao = convert.toModel(dto, petRepository, tutorRepository);
        repository.save(adocao);
        adotarPet(adocao.getAnimal());
        return convert.toDto(adocao);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Pet animal = repository.findById(id).orElseThrow(NoSuchElementException::new).getAnimal();
        repository.deleteById(id);
        adotarPet(animal);
    }

    @Override
    @Transactional
    public Page<AdocaoDtoRes> findAll(Pageable paginacao) {
        Page<Adocao> adocaoPage = repository.findAll(paginacao);
        return adocaoPage.map(AdocaoDtoRes::new);
    }

    private void adotarPet(Pet pet) {
        Pet referenceById = petRepository.findById(pet.getId()).orElseThrow(NoSuchElementException::new);
        referenceById.adotar();
        petRepository.save(referenceById);
    }
}
