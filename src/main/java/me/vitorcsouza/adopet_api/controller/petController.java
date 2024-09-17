package me.vitorcsouza.adopet_api.controller;

import jakarta.validation.Valid;
import me.vitorcsouza.adopet_api.domain.dto.petDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.petDtoRes;
import me.vitorcsouza.adopet_api.domain.service.petService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;

@RestController
@RequestMapping("/pet")
public class petController {
    @Autowired
    private petService service;

    @PostMapping
    public ResponseEntity<petDtoRes> create(@RequestBody @Valid petDtoReq dto, UriComponentsBuilder uri) {
        petDtoRes dtoRes = service.create(dto);
        URI address = uri.path("/pet/{id}").buildAndExpand(dtoRes.id()).toUri();
        return ResponseEntity.created(address).body(dtoRes);
    }

    @GetMapping
    public ResponseEntity<Page<petDtoRes>> findAll(@PageableDefault Pageable pag) {
        Page<petDtoRes> dtoResPage = service.findAll(pag);
        return ResponseEntity.ok(dtoResPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<petDtoRes> findById(@PathVariable Long id) {
        petDtoRes dtoRes = service.findById(id);
        return ResponseEntity.ok(dtoRes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<petDtoRes> update(@PathVariable Long id, @RequestBody petDtoReq dto) {
        petDtoRes updated = service.update(dto, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
