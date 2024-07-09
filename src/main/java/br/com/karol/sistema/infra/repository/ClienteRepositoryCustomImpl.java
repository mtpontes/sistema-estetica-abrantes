package br.com.karol.sistema.infra.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.domain.Procedimento;

@Repository
public class ClienteRepositoryCustomImpl implements ClienteRepositoryCustom{
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    
    public Page<Cliente> findAllByParams(String nome, Pageable pageable) {
        Criteria criteria = new Criteria();
        if (nome != null && !nome.isBlank())
            criteria.and("nome").regex(nome, "i");

        Query query = new Query(criteria).with(pageable);
        List<Cliente> results = mongoTemplate.find(query, Cliente.class);
        long total = mongoTemplate.count(query.skip(0).limit(0), Procedimento.class);

        return new PageImpl<>(results, pageable, total);
    }
}