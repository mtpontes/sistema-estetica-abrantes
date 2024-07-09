package br.com.karol.sistema.domain.validator.cliente.telefone;

import org.springframework.stereotype.Component;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import br.com.karol.sistema.domain.valueobjects.Telefone;
import br.com.karol.sistema.infra.exceptions.InvalidVOException;

@Component
public class PatternTelefoneValidator implements TelefoneValidator{
    
    private static final Class<?> CLASSE = Telefone.class;
    private final PhoneNumberUtil pnUtil = PhoneNumberUtil.getInstance();


    @Override
    public void validate(String value) {
		PhoneNumber pn;
		try {
			pn = pnUtil.parse(value, "BR");
			
		} catch (NumberParseException e) {
			throw new InvalidVOException(CLASSE);
		}
			
        if (!pnUtil.isValidNumberForRegion(pn, "BR"))
            throw new InvalidVOException(CLASSE);
    }
}