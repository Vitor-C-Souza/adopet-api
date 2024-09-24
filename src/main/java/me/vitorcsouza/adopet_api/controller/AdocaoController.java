package me.vitorcsouza.adopet_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import me.vitorcsouza.adopet_api.domain.dto.AdocaoDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.AdocaoDtoRes;
import me.vitorcsouza.adopet_api.domain.service.AdocaoService;
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
@RequestMapping("/adocao")
@SecurityRequirement(name = "bearer-key")
public class AdocaoController {

    @Autowired
    private AdocaoService service;

    @PostMapping
    @Operation(summary = "Cria uma adoção enviando id do abrigo e do pet", tags = {"Adoção"})
    public ResponseEntity<AdocaoDtoRes> create(@RequestBody @Valid AdocaoDtoReq dto, UriComponentsBuilder uri){
        AdocaoDtoRes dtoRes = service.create(dto);
        URI address = uri.path("adocao/{id}").buildAndExpand(dtoRes.id()).toUri();
        return ResponseEntity.created(address).body(dtoRes);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancela uma adoção feita", tags = {"Adoção"}, description = "Só pode ser efetuada por usuarios do tipo abrigo.")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Retorna uma lista de adoções", tags = {"Adoção"})
    public ResponseEntity<Page<AdocaoDtoRes>> findAll(@PageableDefault(size = 10) @ParameterObject Pageable paginacao){
        Page<AdocaoDtoRes> dtoRes = service.findAll(paginacao);
        return ResponseEntity.ok(dtoRes);
    }
}
