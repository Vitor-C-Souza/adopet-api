package me.vitorcsouza.adopet_api.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.vitorcsouza.adopet_api.domain.dto.TutorDtoReq;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "tutor_db")
@Getter
@NoArgsConstructor
public class Tutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String fotoDePerfil;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String telefone;
    @Column(nullable = false)
    private String cidade;
    @Column(length = 400, nullable = false)
    private String sobre;

    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Adocao> adocoes = new ArrayList<>();

    public Tutor(TutorDtoReq dto) {
        createOrUpdate(dto);
    }

    public void createOrUpdate(TutorDtoReq dto) {
        this.fotoDePerfil = dto.fotoDePerfil();
        this.nome = dto.nome();
        this.telefone = dto.telefone();
        this.cidade = dto.cidade();
        this.sobre = dto.sobre();
    }
}
