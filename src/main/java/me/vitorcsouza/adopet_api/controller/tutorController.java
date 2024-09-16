package me.vitorcsouza.adopet_api.controller;

import jakarta.validation.Valid;
import me.vitorcsouza.adopet_api.domain.dto.tutorDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.tutorDtoRes;
import me.vitorcsouza.adopet_api.domain.service.tutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/tutores")
public class tutorController {
    @Autowired
    private tutorService service;
    @PostMapping
    public ResponseEntity<tutorDtoRes> create(@RequestBody @Valid tutorDtoReq dto, UriComponentsBuilder uri){
        tutorDtoRes dtoRes = service.create(dto);
        URI address = uri.path("tutor/{id}").buildAndExpand(dtoRes.id()).toUri();
        return ResponseEntity.created(address).body(dtoRes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<tutorDtoRes> update(@PathVariable Long id, @RequestBody @Valid tutorDtoReq dto){
        tutorDtoRes updated = service.update(dto, id);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<tutorDtoRes> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<tutorDtoRes>> findAll(@PageableDefault Pageable pag){
        Page<tutorDtoRes> dtoResPage = service.FindAll(pag);
        return ResponseEntity.ok(dtoResPage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
