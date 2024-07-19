package br.com.karol.sistema.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.karol.sistema.domain.enums.UserRole;
import br.com.karol.sistema.domain.valueobjects.Login;
import br.com.karol.sistema.domain.valueobjects.Senha;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Entity(name = "Usuario")
@Table(name = "usuarios")
public class Usuario implements UserDetails {
    
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private String nome;

    @Embedded
    @AttributeOverride(
        name = "value", 
        column = @Column(name = "login", unique = true)
    )
    private Login login;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "senha"))
    private Senha senha;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public Usuario(String nome, Login login, Senha senha) {
        this.nome = this.notBlank(nome, "nome");
        this.login = this.notNull(login, "login");
        this.senha = this.notNull(senha, "senha");

        this.role = UserRole.USER;
    }
    public Usuario(String nome, Login login, Senha senha, UserRole role) {
        this(nome, login, senha);
        this.role = this.notNull(role, "role");
    }


    public void atualizarDados(String nome) {
        this.nome = this.notBlank(nome, "nome");
    }
    public void atualizarSenha(Senha senha) {
        this.senha = this.notNull(senha, "senha");;
    }

    private <T> T notNull(T field, String fieldName) {
        if (field == null)
            throw new IllegalArgumentException("Não pode ser null: " + fieldName);
        return field;
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
        if (this.role == UserRole.USER)
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        if (this.role == UserRole.CLIENT)
            return List.of(new SimpleGrantedAuthority("ROLE_CLIENT"));
        return null;
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