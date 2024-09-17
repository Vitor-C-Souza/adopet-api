package me.vitorcsouza.adopet_api.controller;

import jakarta.validation.Valid;
import me.vitorcsouza.adopet_api.domain.dto.abrigoDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.abrigoDtoRes;
import me.vitorcsouza.adopet_api.domain.service.abrigoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/abrigo")
public class abrigoController {
    @Autowired
    private abrigoService service;

    @PostMapping
    public ResponseEntity<abrigoDtoRes> create(@RequestBody @Valid abrigoDtoReq dto, UriComponentsBuilder uri){
        abrigoDtoRes dtoRes = service.create(dto);
        URI address = uri.path("/abrigo/{id}").buildAndExpand(dtoRes.id()).toUri();
        return ResponseEntity.created(address).body(dtoRes);
    }

    @GetMapping
    public ResponseEntity<Page<abrigoDtoRes>> findAll(@PageableDefault Pageable pag){
        Page<abrigoDtoRes> dtoResPage = service.FindAll(pag);
        return ResponseEntity.ok(dtoResPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<abrigoDtoRes> findById(@PathVariable Long id){
        abrigoDtoRes dtoRes = service.findById(id);
        return ResponseEntity.ok(dtoRes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<abrigoDtoRes> update(@PathVariable Long id, @RequestBody @Valid abrigoDtoReq dto) {
        abrigoDtoRes updated = service.update(dto, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
