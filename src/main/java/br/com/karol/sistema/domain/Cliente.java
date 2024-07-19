package br.com.karol.sistema.domain;

import java.util.HashSet;
import java.util.Set;

import br.com.karol.sistema.domain.valueobjects.Cpf;
import br.com.karol.sistema.domain.valueobjects.Email;
import br.com.karol.sistema.domain.valueobjects.Telefone;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Cliente")
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    
    @Embedded
    @AttributeOverride(
        name = "value", 
        column = @Column(name = "cpf", unique = true)
    )
    private Cpf cpf;
    
    @Embedded
    @AttributeOverride(
        name = "value", 
        column = @Column(name = "telefone", unique = true)
    )
    private Telefone telefone;
    
    @Embedded
    @AttributeOverride(
        name = "value", 
        column = @Column(name = "email", unique = true)
    )
    private Email email;

    @Embedded
    private Endereco endereco;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", unique = true)
    private Usuario usuario;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private Set<Agendamento> agendamentos = new HashSet<>();

    public Cliente(
        String nome, 
        Cpf cpf, 
        Telefone telefone, 
        Email email, 
        Endereco endereco
    ) {
        this.nome = this.notBlank(nome, "nome");

        this.cpf = this.notNull(cpf, "cpf");
        this.telefone = this.notNull(telefone, "telefone");
        this.email = this.notNull(email, "email");
        this.endereco = this.notNull(endereco, "endereco");
    }

    public Cliente(
        String nome, 
        Cpf cpf, 
        Telefone telefone, 
        Email email, 
        Endereco endereco, 
        Usuario usuario
    ) {
        this(nome, cpf, telefone, email, endereco);
        this.usuario = this.notNull(usuario, "usuario");
    }

    
    public void atualizarDados(String nome, Telefone telefone, Email email) {
        if (!this.isNull(nome) && !nome.isBlank()) this.nome = nome;

        if (!this.isNull(telefone)) this.telefone = telefone;
        if (!this.isNull(email)) this.email = email;
    }

    public void atualizarEndereco(Endereco endereco) {
        if (!this.isNull(endereco)) this.endereco = endereco;
    }
    
    private boolean isNull(Object param) {
        return param == null;
    }

    private <T> T notNull(T field, String fieldName) {
        if (field == null)
            throw new IllegalArgumentException("Não pode ser null: " + fieldName);
        return field;
    }
    private String notBlank(String param, String fieldName) {
        this.notNull(param, fieldName);
        if (param.isBlank())
            throw new IllegalArgumentException("Não pode ser blank: " + fieldName);
        return param;
    }

    public String getCpf() {
        return this.cpf.getValue();
    }
    public String getTelefone() {
        return this.telefone.getValue();
    }
    public String getEmail() {
        return this.email.getValue();
    }
}