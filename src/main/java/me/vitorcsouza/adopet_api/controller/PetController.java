package me.vitorcsouza.adopet_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import me.vitorcsouza.adopet_api.domain.dto.PetDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.PetDtoRes;
import me.vitorcsouza.adopet_api.domain.service.PetService;
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
@RequestMapping("/pet")
@SecurityRequirement(name = "bearer-key")
public class PetController {
    @Autowired
    private PetService service;

    @PostMapping
    @Operation(summary = "Salva um pet", tags = {"Pet"})
    public ResponseEntity<PetDtoRes> create(@RequestBody @Valid PetDtoReq dto, UriComponentsBuilder uri) {
        PetDtoRes dtoRes = service.create(dto);
        URI address = uri.path("/pet/{id}").buildAndExpand(dtoRes.id()).toUri();
        return ResponseEntity.created(address).body(dtoRes);
    }

    @GetMapping
    @Operation(summary = "Retorna uma lista de pets", tags = {"Pet"})
    public ResponseEntity<Page<PetDtoRes>> findAll(@PageableDefault(size = 10) @ParameterObject Pageable pag) {
        Page<PetDtoRes> dtoResPage = service.findAll(pag);
        return ResponseEntity.ok(dtoResPage);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna um pet pelo seu id", tags = {"Pet"})
    public ResponseEntity<PetDtoRes> findById(@PathVariable Long id) {
        PetDtoRes dtoRes = service.findById(id);
        return ResponseEntity.ok(dtoRes);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um pet determinado pelo id", tags = {"Pet"})
    public ResponseEntity<PetDtoRes> update(@PathVariable Long id, @RequestBody PetDtoReq dto) {
        PetDtoRes updated = service.update(dto, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um pet determinado pelo seu id", tags = {"Pet"})
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
