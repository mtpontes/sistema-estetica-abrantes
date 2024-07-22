package br.com.karol.sistema.constants;

import java.time.LocalDateTime;

public class TestConstants {
    public static final String BASE_URL = "/usuarios";
    public static final String ADMIN_ROUTE = BASE_URL + "/admin";
    
    public static final String NOME = "nome";
    public static final String LOGIN = "login"; // mais de 3 caracteres
    public static final String SENHA = "senha1234"; // mais de 8 caracteres
    public static final String CPF = "12345678911"; // 11 digitos
    public static final String TELEFONE = "12345678911"; // 11 digitos
    public static final String EMAIL = "email";

    public static final String NOME_VAZIO = "";
    public static final String LOGIN_MUITO_PEQUENO = "lo"; // menos de 3 caracteres
    public static final String LOGIN_MUITO_GRANDE = "loginnnnnnnnnnnnnnnnn"; // mais de 20 caracteres
    public static final String SENHA_MUITO_PEQUENA = "senha12"; // menos de 8 caracteres 
    public static final String SENHA_MUITO_GRANDE = "senha1234567891234567"; // mais de 20 caracteres
    public static final String CPF_MUITO_PEQUENO = "1234567891"; // menos de 11 caracteres
    public static final String CPF_MUITO_GRANDE = "123456789123456"; // mais de 14 caracteres
    public static final String TELEFONE_MUITO_PEQUENO = "1234567891"; // menos de 11 caracteres
    public static final String TELEFONE_MUITO_GRANDE = "1234567891234567"; // mais de 15 caracteres
    public static final String EMAIL_VAZIO = "";

    public static final LocalDateTime FUTURO = LocalDateTime.now().plusDays(1);
    public static final LocalDateTime PASSADO = LocalDateTime.now().minusDays(1);

    public static final String CPF_FORMATADO = "123.456.789-01";

}