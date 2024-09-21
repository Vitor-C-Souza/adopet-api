package me.vitorcsouza.adopet_api.controller;

import jakarta.validation.Valid;
import me.vitorcsouza.adopet_api.domain.dto.PetDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.PetDtoRes;
import me.vitorcsouza.adopet_api.domain.service.PetService;
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
public class PetController {
    @Autowired
    private PetService service;

    @PostMapping
    public ResponseEntity<PetDtoRes> create(@RequestBody @Valid PetDtoReq dto, UriComponentsBuilder uri) {
        PetDtoRes dtoRes = service.create(dto);
        URI address = uri.path("/pet/{id}").buildAndExpand(dtoRes.id()).toUri();
        return ResponseEntity.created(address).body(dtoRes);
    }

    @GetMapping
    public ResponseEntity<Page<PetDtoRes>> findAll(@PageableDefault(size = 10) Pageable pag) {
        Page<PetDtoRes> dtoResPage = service.findAll(pag);
        return ResponseEntity.ok(dtoResPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDtoRes> findById(@PathVariable Long id) {
        PetDtoRes dtoRes = service.findById(id);
        return ResponseEntity.ok(dtoRes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDtoRes> update(@PathVariable Long id, @RequestBody PetDtoReq dto) {
        PetDtoRes updated = service.update(dto, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
