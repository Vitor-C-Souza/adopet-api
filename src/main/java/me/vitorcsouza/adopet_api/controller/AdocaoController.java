package me.vitorcsouza.adopet_api.controller;

import jakarta.validation.Valid;
import me.vitorcsouza.adopet_api.domain.dto.AdocaoDtoReq;
import me.vitorcsouza.adopet_api.domain.dto.AdocaoDtoRes;
import me.vitorcsouza.adopet_api.domain.service.AdocaoService;
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
public class AdocaoController {

    @Autowired
    private AdocaoService service;

    @PostMapping
    public ResponseEntity<AdocaoDtoRes> create(@RequestBody @Valid AdocaoDtoReq dto, UriComponentsBuilder uri){
        AdocaoDtoRes dtoRes = service.create(dto);
        URI address = uri.path("adocao/{id}").buildAndExpand(dtoRes.id()).toUri();
        return ResponseEntity.created(address).body(dtoRes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<AdocaoDtoRes>> findAll(@PageableDefault(size = 10) Pageable paginacao){
        Page<AdocaoDtoRes> dtoRes = service.findAll(paginacao);
        return ResponseEntity.ok(dtoRes);
    }
}
