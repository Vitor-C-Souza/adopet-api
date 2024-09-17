package me.vitorcsouza.adopet_api.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.vitorcsouza.adopet_api.domain.dto.PetDtoReq;
import me.vitorcsouza.adopet_api.domain.repository.AbrigoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Entity
@Table(name = "pet_tb")
@Getter
@NoArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String descricao;
    @Column(nullable = false)
    private boolean adotado = false;
    @Column(nullable = false)
    private String idade;
    @Column(nullable = false)
    private String endereco;
    @Column(nullable = false)
    private String imagem;
    @ManyToOne(fetch = FetchType.LAZY)
    private Abrigo abrigo;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Adocao> adocoes = new ArrayList<>();

    public Pet(PetDtoReq dto, AbrigoRepository repository) {
        createOrUpdate(dto, repository);
    }

    public void createOrUpdate(PetDtoReq dto, AbrigoRepository repository) {
        this.nome = dto.nome();
        this.descricao = dto.descricao();
        this.idade = dto.idade();
        this.endereco = dto.endereco();
        this.imagem = dto.imagem();
        this.abrigo = repository.findById(dto.abrigos_id()).orElseThrow(NoSuchElementException::new);
        if(dto.adotado()){
            this.adotado = true;
        }
    }

    public void adotar(){
        this.adotado = true;
    }
}
