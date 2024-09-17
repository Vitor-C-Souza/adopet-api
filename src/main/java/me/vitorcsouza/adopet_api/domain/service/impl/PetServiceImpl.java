package me.vitorcsouza.adopet_api.domain.service.impl;

import jakarta.transaction.Transactional;
import me.vitorcsouza.adopet_api.domain.dto.PetDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.PetDtoRes;
import me.vitorcsouza.adopet_api.domain.model.Pet;
import me.vitorcsouza.adopet_api.domain.repository.AbrigoRepository;
import me.vitorcsouza.adopet_api.domain.repository.PetRepository;
import me.vitorcsouza.adopet_api.domain.service.conversor.ConvertPet;
import me.vitorcsouza.adopet_api.domain.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PetServiceImpl implements PetService {
    @Autowired
    private PetRepository repository;
    @Autowired
    private ConvertPet convert;
    @Autowired
    private AbrigoRepository repositoryAbrigo;

    @Override
    public PetDtoRes create(PetDtoReq dto) {
        Pet pet = convert.toModel(dto, repositoryAbrigo);
        repository.save(pet);
        return convert.toDto(pet);
    }

    @Override
    @Transactional
    public PetDtoRes findById(Long id) {
        Optional<Pet> optionalPet = repository.findByIdAvailable(id);
        if (optionalPet.isPresent()) {
            return convert.toDto(optionalPet.get());
        }

        throw new NoSuchElementException();
    }

    @Override
    @Transactional
    public Page<PetDtoRes> findAll(Pageable pag) {
        Page<Pet> petPage = repository.findAllAvailable(pag);
        return petPage.map(PetDtoRes::new);
    }

    @Override
    @Transactional
    public PetDtoRes update(PetDtoReq dto, Long id) {
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
