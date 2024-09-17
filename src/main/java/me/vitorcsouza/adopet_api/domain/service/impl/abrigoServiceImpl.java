package me.vitorcsouza.adopet_api.domain.service.impl;

import jakarta.transaction.Transactional;
import me.vitorcsouza.adopet_api.domain.dto.abrigoDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.abrigoDtoRes;
import me.vitorcsouza.adopet_api.domain.model.Abrigo;
import me.vitorcsouza.adopet_api.domain.repository.abrigoRepository;
import me.vitorcsouza.adopet_api.domain.service.abrigoService;
import me.vitorcsouza.adopet_api.domain.service.conversor.convertAbrigo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class abrigoServiceImpl implements abrigoService {
    @Autowired
    private abrigoRepository repository;
    @Autowired
    private convertAbrigo convert;

    @Override
    public abrigoDtoRes create(abrigoDtoReq dto) {
        Abrigo abrigo = convert.toModel(dto);
        repository.save(abrigo);
        return convert.toDto(abrigo);
    }

    @Override
    public abrigoDtoRes findById(Long id) {
        Optional<Abrigo> optionalAbrigo = repository.findById(id);
        if (optionalAbrigo.isPresent()) {
            return convert.toDto(optionalAbrigo.get());
        }
        throw new NoSuchElementException();
    }

    @Override
    public Page<abrigoDtoRes> FindAll(Pageable pag) {
        Page<Abrigo> abrigos = repository.findAll(pag);
        return abrigos.map(abrigoDtoRes::new);
    }

    @Transactional
    @Override
    public abrigoDtoRes update(abrigoDtoReq dto, Long id) {
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
