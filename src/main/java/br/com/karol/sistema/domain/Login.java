package br.com.karol.sistema.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Login")
@Table(name = "logins")
public class Login {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    public Login(String login, String password) {
        setLogin(login);
        setPassword(password);
    }

    public void atualizarSenha(String password) {
        setPassword(password);
    }

    private void notBlank(String field, String fieldName) {
        if (field == null)
            throw new IllegalArgumentException("Não pode ser null: " + fieldName);
        if (field.isBlank())
            throw new IllegalArgumentException("Não pode ser blank: " + fieldName);
    }

    public void setLogin(String login) {
        notBlank(login, "login");
        this.login = login;
    }
    public void setPassword(String password) {
        this.notBlank(password, "password");
        this.password = password;
    }
}