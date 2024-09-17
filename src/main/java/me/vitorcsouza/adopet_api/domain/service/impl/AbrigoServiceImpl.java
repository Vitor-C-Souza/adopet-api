package me.vitorcsouza.adopet_api.domain.service.impl;

import jakarta.transaction.Transactional;
import me.vitorcsouza.adopet_api.domain.dto.AbrigoDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.AbrigoDtoRes;
import me.vitorcsouza.adopet_api.domain.model.Abrigo;
import me.vitorcsouza.adopet_api.domain.repository.AbrigoRepository;
import me.vitorcsouza.adopet_api.domain.service.AbrigoService;
import me.vitorcsouza.adopet_api.domain.service.conversor.ConvertAbrigo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AbrigoServiceImpl implements AbrigoService {
    @Autowired
    private AbrigoRepository repository;
    @Autowired
    private ConvertAbrigo convert;

    @Override
    public AbrigoDtoRes create(AbrigoDtoReq dto) {
        Abrigo abrigo = convert.toModel(dto);
        repository.save(abrigo);
        return convert.toDto(abrigo);
    }

    @Override
    public AbrigoDtoRes findById(Long id) {
        Optional<Abrigo> optionalAbrigo = repository.findById(id);
        if (optionalAbrigo.isPresent()) {
            return convert.toDto(optionalAbrigo.get());
        }
        throw new NoSuchElementException();
    }

    @Override
    public Page<AbrigoDtoRes> findAll(Pageable pag) {
        Page<Abrigo> abrigos = repository.findAll(pag);
        return abrigos.map(AbrigoDtoRes::new);
    }

    @Transactional
    @Override
    public AbrigoDtoRes update(AbrigoDtoReq dto, Long id) {
        Abrigo referenceById = repository.getReferenceById(id);
        referenceById.createOrUpdate(dto);
        repository.save(referenceById);
        return convert.toDto(referenceById);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
