package me.vitorcsouza.adopet_api.domain.service.impl;

import jakarta.transaction.Transactional;
import me.vitorcsouza.adopet_api.domain.dto.tutorDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.tutorDtoRes;
import me.vitorcsouza.adopet_api.domain.model.Tutor;
import me.vitorcsouza.adopet_api.domain.repository.tutorRepository;
import me.vitorcsouza.adopet_api.domain.service.conversor.convertTutor;
import me.vitorcsouza.adopet_api.domain.service.tutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class tutorServiceImpl implements tutorService {
    @Autowired
    private tutorRepository repository;
    @Autowired
    private convertTutor convert;

    @Override
    public tutorDtoRes create(tutorDtoReq dto) {
        Tutor tutor = convert.toModel(dto);
        repository.save(tutor);
        return convert.toDto(tutor);
    }

    @Override
    public tutorDtoRes findById(Long id) {
        Optional<Tutor> tutor = repository.findById(id);
        if (tutor.isPresent()) {
            return convert.toDto(tutor.get());
        }
        throw new NoSuchElementException();
    }

    @Override
    public Page<tutorDtoRes> FindAll(Pageable pag) {
        Page<Tutor> tutors = repository.findAll(pag);
        if(tutors.isEmpty()){
            throw new NoSuchElementException();
        }
        return tutors.map(tutorDtoRes::new);
    }

    @Override
    @Transactional
    public tutorDtoRes update(tutorDtoReq dto, Long id) {
        Tutor referenceById = repository.getReferenceById(id);
        referenceById.createOrUpdate(dto);
        repository.save(referenceById);
        return convert.toDto(referenceById);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
