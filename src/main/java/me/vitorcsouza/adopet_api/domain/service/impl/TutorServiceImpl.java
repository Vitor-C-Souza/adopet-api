package me.vitorcsouza.adopet_api.domain.service.impl;

import jakarta.transaction.Transactional;
import me.vitorcsouza.adopet_api.domain.dto.TutorDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.TutorDtoRes;
import me.vitorcsouza.adopet_api.domain.model.Tutor;
import me.vitorcsouza.adopet_api.domain.repository.TutorRepository;
import me.vitorcsouza.adopet_api.domain.service.conversor.ConvertTutor;
import me.vitorcsouza.adopet_api.domain.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TutorServiceImpl implements TutorService {
    @Autowired
    private TutorRepository repository;
    @Autowired
    private ConvertTutor convert;

    @Override
    public TutorDtoRes create(TutorDtoReq dto) {
        Tutor tutor = convert.toModel(dto);
        repository.save(tutor);
        return convert.toDto(tutor);
    }

    @Override
    public TutorDtoRes findById(Long id) {
        Optional<Tutor> tutor = repository.findById(id);
        if (tutor.isPresent()) {
            return convert.toDto(tutor.get());
        }
        throw new NoSuchElementException();
    }

    @Override
    public Page<TutorDtoRes> FindAll(Pageable pag) {
        Page<Tutor> tutors = repository.findAll(pag);
        if(tutors.isEmpty()){
            throw new NoSuchElementException();
        }
        return tutors.map(TutorDtoRes::new);
    }

    @Override
    @Transactional
    public TutorDtoRes update(TutorDtoReq dto, Long id) {
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
