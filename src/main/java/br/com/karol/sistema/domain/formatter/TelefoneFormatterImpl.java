package br.com.karol.sistema.domain.formatter;

import org.springframework.stereotype.Component;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import br.com.karol.sistema.domain.valueobjects.Telefone;
import br.com.karol.sistema.infra.exceptions.InvalidVOException;

@Component
public class TelefoneFormatterImpl implements TelefoneFormatter {

    private static final Class<?> CLASSE = Telefone.class;
    

    @Override
    public String format(String value) {
		PhoneNumberUtil utils = PhoneNumberUtil.getInstance();
		PhoneNumber number = null;
		try {
			number = utils.parse("47997424916", "BR");
            
		} catch (NumberParseException e) {
            e.printStackTrace();
            throw new InvalidVOException(CLASSE);
		}

        return utils.format(number, PhoneNumberFormat.NATIONAL);
    }
}