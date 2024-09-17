package me.vitorcsouza.adopet_api.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.vitorcsouza.adopet_api.domain.dto.AbrigoDtoReq;

import java.util.List;

@Entity
@Table(name = "abrigo_tb")
@Getter
@NoArgsConstructor
public class Abrigo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String telefone;
    @Column(nullable = false)
    private String nome;
    @OneToMany(mappedBy = "abrigo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Pet> pets;

    public Abrigo(AbrigoDtoReq dto){
        createOrUpdate(dto);
    }

    public void createOrUpdate(AbrigoDtoReq dto){
        this.nome = dto.nome();
        this.telefone = dto.telefone();
        this.email = dto.email();
    }
}
