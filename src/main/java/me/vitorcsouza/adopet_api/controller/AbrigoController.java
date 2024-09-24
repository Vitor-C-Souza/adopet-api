package me.vitorcsouza.adopet_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import me.vitorcsouza.adopet_api.domain.dto.AbrigoDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.AbrigoDtoRes;
import me.vitorcsouza.adopet_api.domain.service.AbrigoService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.net.URI;

@RestController
@RequestMapping("/abrigo")
@SecurityRequirement(name = "bearer-key")
public class AbrigoController {
    @Autowired
    private AbrigoService service;

    @PostMapping
    @Operation(summary = "Cria novo abrigo", tags = {"Abrigo"})
    public ResponseEntity<AbrigoDtoRes> create(@RequestBody @Valid AbrigoDtoReq dto, UriComponentsBuilder uri){
        AbrigoDtoRes dtoRes = service.create(dto);
        URI address = uri.path("/abrigo/{id}").buildAndExpand(dtoRes.id()).toUri();
        return ResponseEntity.created(address).body(dtoRes);
    }

    @GetMapping
    @Operation(summary = "Encontra todos os abrigos", tags = {"Abrigo"})
    public ResponseEntity<Page<AbrigoDtoRes>> findAll(@PageableDefault @ParameterObject Pageable pag){
        Page<AbrigoDtoRes> dtoResPage = service.findAll(pag);
        return ResponseEntity.ok(dtoResPage);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Encontra abrigo pelo id", tags = {"Abrigo"})
    public ResponseEntity<AbrigoDtoRes> findById(@PathVariable Long id){
        AbrigoDtoRes dtoRes = service.findById(id);
        return ResponseEntity.ok(dtoRes);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza abrigo determinado pelo id", tags = {"Abrigo"})
    public ResponseEntity<AbrigoDtoRes> update(@PathVariable Long id, @RequestBody @Valid AbrigoDtoReq dto) {
        AbrigoDtoRes updated = service.update(dto, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta abrigo determinado pelo id", tags = {"Abrigo"})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
