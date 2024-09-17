package me.vitorcsouza.adopet_api.controller;

import jakarta.validation.Valid;
import me.vitorcsouza.adopet_api.domain.dto.TutorDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.TutorDtoRes;
import me.vitorcsouza.adopet_api.domain.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/tutor")
public class TutorController {
    @Autowired
    private TutorService service;
    @PostMapping
    public ResponseEntity<TutorDtoRes> create(@RequestBody @Valid TutorDtoReq dto, UriComponentsBuilder uri){
        TutorDtoRes dtoRes = service.create(dto);
        URI address = uri.path("tutor/{id}").buildAndExpand(dtoRes.id()).toUri();
        return ResponseEntity.created(address).body(dtoRes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TutorDtoRes> update(@PathVariable Long id, @RequestBody @Valid TutorDtoReq dto){
        TutorDtoRes updated = service.update(dto, id);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutorDtoRes> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<TutorDtoRes>> findAll(@PageableDefault Pageable pag){
        Page<TutorDtoRes> dtoResPage = service.findAll(pag);
        return ResponseEntity.ok(dtoResPage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
