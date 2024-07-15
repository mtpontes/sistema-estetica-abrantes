package br.com.karol.sistema.infra.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.karol.sistema.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByCpfValue(String cpf);

    boolean existsByTelefoneValue(String telefone);

    boolean existsByEmailValue(String email);

    @Query("""
        SELECT c FROM Cliente c WHERE
        (:nomeCliente IS NULL OR LOWER(c.nome) LIKE LOWER(CONCAT('%', :nomeCliente, '%')))        
        """)
    Page<Cliente> findAllByParams(String nomeCliente, Pageable pageable);

    Optional<Cliente> findByUsuarioId(Long usuarioId);
}