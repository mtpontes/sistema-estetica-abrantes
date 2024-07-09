package br.com.karol.sistema.infra.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.domain.enums.StatusAgendamento;

@Repository
public class AgendamentoRepositoryCustomImpl implements AgendamentoRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public Page<Agendamento> findAllByParams(
        StatusAgendamento status, 
        LocalDateTime minDataHora, 
        LocalDateTime maxDataHora, 
        String procedimentoId,
        String clienteId, 
        Pageable pageable
    ) {
        Criteria criteria = new Criteria();

        if (isNotNull(status)) {
            criteria.and("status").is(status);
        }
        if (isNotNull(minDataHora)) {
            criteria.and("dataHora").gte(minDataHora);
        }
        if (isNotNull(maxDataHora)) {
            criteria.and("dataHora").lte(maxDataHora);
        }
        if (isNotBlank(procedimentoId)) {
            criteria.and("procedimento._id").is(procedimentoId);
        }
        if (isNotBlank(clienteId)) {
            criteria.and("cliente._id").is(clienteId);
        }

        Query query = new Query(criteria).with(pageable);
        List<Agendamento> results = mongoTemplate.find(query, Agendamento.class);
        long total = mongoTemplate.count(query.skip(0).limit(0), Agendamento.class);
        
        return new PageImpl<>(results, pageable, total);
    }

    private boolean isNotNull(Object value) {
        return value != null;
    }
    private boolean isNotBlank(String value) {
        return isNotNull(value) && !value.isBlank();
    }
}