package me.vitorcsouza.adopet_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import me.vitorcsouza.adopet_api.domain.dto.TutorDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.TutorDtoRes;
import me.vitorcsouza.adopet_api.domain.service.TutorService;
import org.springdoc.core.annotations.ParameterObject;
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
@SecurityRequirement(name = "bearer-key")
public class TutorController {
    @Autowired
    private TutorService service;
    @PostMapping
    @Operation(summary = "Cria um tutor", tags = {"Tutor"})
    public ResponseEntity<TutorDtoRes> create(@RequestBody @Valid TutorDtoReq dto, UriComponentsBuilder uri){
        TutorDtoRes dtoRes = service.create(dto);
        URI address = uri.path("tutor/{id}").buildAndExpand(dtoRes.id()).toUri();
        return ResponseEntity.created(address).body(dtoRes);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um tutor determinado pelo id", tags = {"Tutor"})
    public ResponseEntity<TutorDtoRes> update(@PathVariable Long id, @RequestBody @Valid TutorDtoReq dto){
        TutorDtoRes updated = service.update(dto, id);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna um tutor pelo seu id", tags = {"Tutor"})
    public ResponseEntity<TutorDtoRes> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    @Operation(summary = "Retorna uma lista de tutores", tags = {"Tutor"})
    public ResponseEntity<Page<TutorDtoRes>> findAll(@PageableDefault @ParameterObject Pageable pag){
        Page<TutorDtoRes> dtoResPage = service.findAll(pag);
        return ResponseEntity.ok(dtoResPage);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um tutor determinado pelo id", tags = {"Tutor"})
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
