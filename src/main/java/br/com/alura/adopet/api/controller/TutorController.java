package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.TutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.service.TutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tutores")
public class TutorController {

    @Autowired
    TutorService tutorService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid TutorDto tutor) {
        try {
            tutorService.cadastrar(tutor);
            return ResponseEntity.ok().build();
        } catch (ValidacaoException exception){
            return  ResponseEntity.badRequest().body(exception.getMessage());
        }

    }

    @PutMapping
    @Transactional
    public ResponseEntity<String> atualizar(@RequestBody @Valid TutorDto dto) {
        try{
            tutorService.atualizar(dto);
            return ResponseEntity.ok().build();
        } catch(ValidacaoException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

}
