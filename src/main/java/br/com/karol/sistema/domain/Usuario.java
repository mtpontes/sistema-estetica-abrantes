package br.com.karol.sistema.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.karol.sistema.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Usuario")
@Table(name = "usuarios")
public class Usuario implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;
    
    @Column(unique = true, nullable = false)
    private String login;
    
    @Column(nullable = false)
    private String senha;

    @Setter
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public Usuario(String nome, String login, String senha) {
        this.setNome(nome);
        this.setLogin(login);
        this.setSenha(senha);
        this.role = UserRole.USER;
    }


    public void atualizarDados(String nome, String senha) {
        this.setNome(nome);
        this.setSenha(senha);
    }

    public void setNome(String nome) {
        this.notBlank(nome, "nome");
        this.senha = nome;
    }

    public void setLogin(String login) {
        this.notBlank(login, "login");
        this.login = login;
    }

    public void setSenha(String senha) {
        this.notBlank(senha, "senha");
        this.senha = senha;
    }

    private void notNull(Object field, String fieldName) {
        if (field == null) 
            throw new IllegalArgumentException("Não pode ser null: " + fieldName);
    }
    private void notBlank(String field, String fieldName) {
        this.notNull(field, fieldName);
        if (field.isBlank())
            throw new IllegalArgumentException("Não pode ser blank: " + fieldName);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN)
            return List.of(
                new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_USER")
            );
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
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