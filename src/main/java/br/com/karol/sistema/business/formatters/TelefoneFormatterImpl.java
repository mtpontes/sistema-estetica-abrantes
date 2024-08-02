package br.com.karol.sistema.business.formatters;

import org.springframework.stereotype.Component;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import br.com.karol.sistema.domain.formatter.TelefoneFormatter;
import br.com.karol.sistema.domain.valueobjects.Telefone;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;

@Component
public class TelefoneFormatterImpl implements TelefoneFormatter {

    private static final String CLASSE = Telefone.class.getSimpleName();
    

    @Override
    public String format(String value) {
		PhoneNumberUtil utils = PhoneNumberUtil.getInstance();
		PhoneNumber number = null;
		try {
			number = utils.parse(value, "BR");
            
		} catch (NumberParseException e) {
            e.printStackTrace();
            throw new FieldValidationException(CLASSE);
		}

        return utils.format(number, PhoneNumberFormat.NATIONAL);
    }
}