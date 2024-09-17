package me.vitorcsouza.adopet_api.domain.service.impl;

import jakarta.transaction.Transactional;
import me.vitorcsouza.adopet_api.domain.dto.petDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.petDtoRes;
import me.vitorcsouza.adopet_api.domain.model.Pet;
import me.vitorcsouza.adopet_api.domain.repository.abrigoRepository;
import me.vitorcsouza.adopet_api.domain.repository.petRepository;
import me.vitorcsouza.adopet_api.domain.service.conversor.convertPet;
import me.vitorcsouza.adopet_api.domain.service.petService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class petServiceImpl implements petService {
    @Autowired
    private petRepository repository;
    @Autowired
    private convertPet convert;
    @Autowired
    private abrigoRepository repositoryAbrigo;

    @Override
    public petDtoRes create(petDtoReq dto) {
        Pet pet = convert.toModel(dto, repositoryAbrigo);
        repository.save(pet);
        return convert.toDto(pet);
    }

    @Override
    @Transactional
    public petDtoRes findById(Long id) {
        Optional<Pet> optionalPet = repository.findById(id);
        if (optionalPet.isPresent()) {
            return convert.toDto(optionalPet.get());
        }

        throw new NoSuchElementException();
    }

    @Override
    @Transactional
    public Page<petDtoRes> findAll(Pageable pag) {
        Page<Pet> petPage = repository.findAll(pag);
        return petPage.map(petDtoRes::new);
    }

    @Override
    @Transactional
    public petDtoRes update(petDtoReq dto, Long id) {
        Pet referenceById = repository.getReferenceById(id);
        referenceById.createOrUpdate(dto, repositoryAbrigo);
        repository.save(referenceById);
        return convert.toDto(referenceById);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
