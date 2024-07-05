package br.com.karol.sistema.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.karol.sistema.domain.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usuarios", collation = "pt", language = "portuguese")
public class Usuario implements UserDetails {
    
    @Id
    private String id;
    private String nome;

    @Indexed(unique = true)
    private String login;
    private String senha;
    
    @Setter 
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
        this.nome = nome;
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