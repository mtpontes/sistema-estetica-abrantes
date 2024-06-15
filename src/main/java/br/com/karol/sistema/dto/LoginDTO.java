package br.com.karol.sistema.dto;

import br.com.karol.sistema.domain.Login;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data

public class LoginDTO {

    private int id;
    private String login;
    private String password;

    public LoginDTO(Login login) {
        BeanUtils.copyProperties(login, this);
    }

}
