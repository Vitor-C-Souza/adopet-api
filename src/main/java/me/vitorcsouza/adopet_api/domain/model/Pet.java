package me.vitorcsouza.adopet_api.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.vitorcsouza.adopet_api.domain.dto.petDtoReq;
import me.vitorcsouza.adopet_api.domain.repository.abrigoRepository;

import java.util.NoSuchElementException;

@Entity
@Table(name = "pet_tb")
@Getter
@NoArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private boolean adotado = false;
    private String idade;
    private String endereco;
    private String imagem;
    @ManyToOne(fetch = FetchType.LAZY)
    private Abrigo abrigo;

    public Pet(petDtoReq dto, abrigoRepository repository) {
        createOrUpdate(dto, repository);
    }

    public void createOrUpdate(petDtoReq dto, abrigoRepository repository) {
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
}
