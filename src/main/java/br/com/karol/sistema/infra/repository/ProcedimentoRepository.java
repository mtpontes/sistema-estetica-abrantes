package br.com.karol.sistema.infra.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.karol.sistema.domain.Procedimento;

public interface ProcedimentoRepository extends JpaRepository<Procedimento, Long> {

    boolean existsByNome(String nome);

    @Query(
        """
        SELECT p FROM Procedimento p WHERE
        (:procedimentoNome IS NULL OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :procedimentoNome, '%')))
        AND (:minValor IS NULL OR p.valor >= :minValor)
        AND (:maxValor IS NULL OR p.valor <= :minValor)
        """)
    Page<Procedimento> findAllByParams(String procedimentoNome, Double minValor, Double maxValor, Pageable pageable);
}