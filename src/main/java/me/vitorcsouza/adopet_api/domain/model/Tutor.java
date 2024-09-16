package me.vitorcsouza.adopet_api.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.vitorcsouza.adopet_api.domain.dto.tutorDtoReq;

@Entity(name = "tutor_db")
@Getter
@NoArgsConstructor
public class Tutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fotoDePerfil;
    private String nome;
    private String telefone;
    private String cidade;
    @Column(length = 400)
    private String sobre;

    public Tutor(tutorDtoReq dto) {
        createOrUpdate(dto);
    }

    public void createOrUpdate(tutorDtoReq dto) {
        this.fotoDePerfil = dto.fotoDePerfil();
        this.nome = dto.nome();
        this.telefone = dto.telefone();
        this.cidade = dto.cidade();
        this.sobre = dto.sobre();
    }
}
