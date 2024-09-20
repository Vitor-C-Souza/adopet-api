package me.vitorcsouza.adopet_api.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import me.vitorcsouza.adopet_api.domain.dto.CadastroDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "perfil_tb")
@Getter
public class Login implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String usuario;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String senha;
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    public Login(CadastroDto dto, String senha, String tipo) {
        this.usuario = dto.usuario();
        this.email = dto.email();
        this.senha = senha;
        this.tipo = Tipo.valueOf(tipo.toUpperCase());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.usuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
