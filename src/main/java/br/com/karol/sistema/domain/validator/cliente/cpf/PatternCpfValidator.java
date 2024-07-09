package br.com.karol.sistema.domain.validator.cliente.cpf;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.stereotype.Component;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import br.com.karol.sistema.infra.exceptions.InvalidVOException;

@Component
public class PatternCpfValidator implements CpfValidator{

    private static final Class<?> CLASSE = CPF.class;


    @Override
    public void validate(String value) {
        CPFValidator cpfValidator = new CPFValidator(); 
        try{ 
            cpfValidator.assertValid(value); 

        } catch(InvalidStateException e) {
            throw new InvalidVOException(CLASSE);
        } 
    }
}