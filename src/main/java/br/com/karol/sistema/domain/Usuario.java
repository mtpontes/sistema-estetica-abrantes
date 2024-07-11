package br.com.karol.sistema.domain;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.karol.sistema.domain.enums.UserRole;
import br.com.karol.sistema.domain.valueobjects.Login;
import br.com.karol.sistema.domain.valueobjects.Senha;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usuarios", collation = "pt", language = "portuguese")
public class Usuario implements UserDetails {
    
    @Id @Getter
    private String id;
    @Getter
    private String nome;
    @Indexed(unique = true)
    private Login login;
    private Senha senha;
    @Getter @Setter 
    private UserRole role;

    public Usuario(String nome, Login login, Senha senha) {
        this.nome = this.notBlank(nome, "nome");
        this.login = this.notNull(login, "login");
        this.senha = this.notNull(senha, "senha");

        this.role = UserRole.USER;
    }


    public void atualizarDados(String nome) {
        this.notBlank(nome, "nome");
        this.nome = nome;
    }
    public void atualizarSenha(Senha senha) {
        this.notNull(senha, "senha");
        this.senha = senha;
    }

    private <T> T notNull(T field, String fieldName) {
        return Objects.requireNonNull(field, "Não pode ser null: " + fieldName);
    }
    private String notBlank(String field, String fieldName) {
        this.notNull(field, fieldName);
        if (field.isBlank())
            throw new IllegalArgumentException("Não pode ser blank: " + fieldName);
        return field;
    }

    public String getLogin() {
        return this.login.getValue();
    }
    public String getSenha() {
        return this.senha.getValue();
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
        return this.senha.getValue();
    }

    @Override
    public String getUsername() {
        return this.login.getValue();
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