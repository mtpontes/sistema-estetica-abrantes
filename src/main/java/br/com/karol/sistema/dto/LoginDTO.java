package br.com.karol.sistema.dto;

import br.com.karol.sistema.domain.Login;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    private int id;
    private String login;
    private String password;

    public LoginDTO(Login login) {
        BeanUtils.copyProperties(login, this);
    }

}
