package me.vitorcsouza.adopet_api.controller;

import jakarta.validation.Valid;
import me.vitorcsouza.adopet_api.domain.dto.AbrigoDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.AbrigoDtoRes;
import me.vitorcsouza.adopet_api.domain.service.AbrigoService;
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
public class AbrigoController {
    @Autowired
    private AbrigoService service;

    @PostMapping
    public ResponseEntity<AbrigoDtoRes> create(@RequestBody @Valid AbrigoDtoReq dto, UriComponentsBuilder uri){
        AbrigoDtoRes dtoRes = service.create(dto);
        URI address = uri.path("/abrigo/{id}").buildAndExpand(dtoRes.id()).toUri();
        return ResponseEntity.created(address).body(dtoRes);
    }

    @GetMapping
    public ResponseEntity<Page<AbrigoDtoRes>> findAll(@PageableDefault Pageable pag){
        Page<AbrigoDtoRes> dtoResPage = service.findAll(pag);
        return ResponseEntity.ok(dtoResPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbrigoDtoRes> findById(@PathVariable Long id){
        AbrigoDtoRes dtoRes = service.findById(id);
        return ResponseEntity.ok(dtoRes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AbrigoDtoRes> update(@PathVariable Long id, @RequestBody @Valid AbrigoDtoReq dto) {
        AbrigoDtoRes updated = service.update(dto, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
