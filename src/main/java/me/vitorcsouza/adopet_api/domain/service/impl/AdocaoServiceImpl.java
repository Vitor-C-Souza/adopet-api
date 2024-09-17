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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    @Transactional
    public AdocaoDtoRes create(AdocaoDtoReq dto) {
        Adocao adocao = convert.toModel(dto, petRepository, tutorRepository);
        repository.save(adocao);
        adotarPet(adocao);
        return convert.toDto(adocao);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private void adotarPet(Adocao adocao) {
        Pet referenceById = petRepository.getReferenceById(adocao.getAnimal().getId());
        referenceById.adotar();
        petRepository.save(referenceById);
    }
}
