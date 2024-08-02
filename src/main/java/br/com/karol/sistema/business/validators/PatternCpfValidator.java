package br.com.karol.sistema.business.validators;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.stereotype.Component;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import br.com.karol.sistema.domain.validator.CpfValidator;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;

@Component
public class PatternCpfValidator implements CpfValidator{

    private static final String CLASSE = CPF.class.getSimpleName();


    @Override
    public void validate(String value) {
        CPFValidator cpfValidator = new CPFValidator(); 
        try{ 
            cpfValidator.assertValid(value);

        } catch(InvalidStateException e) {
            throw new FieldValidationException(CLASSE);
        } 
    }
}