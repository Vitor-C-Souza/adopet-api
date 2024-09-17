package me.vitorcsouza.adopet_api.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.vitorcsouza.adopet_api.domain.dto.abrigoDtoReq;

import java.util.List;

@Entity
@Table(name = "abrigo_tb")
@Getter
@NoArgsConstructor
public class Abrigo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String telefone;
    private String nome;
    @OneToMany(mappedBy = "abrigo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Pet> pets;

    public Abrigo(abrigoDtoReq dto){
        createOrUpdate(dto);
    }

    public void createOrUpdate(abrigoDtoReq dto){
        this.nome = dto.nome();
        this.telefone = dto.telefone();
        this.email = dto.email();
    }
}
