package br.com.karol.sistema.unit.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;

import br.com.karol.sistema.api.controller.AuthenticationController;
import br.com.karol.sistema.api.dto.authentication.AuthenticationDTO;
import br.com.karol.sistema.business.service.TokenService;
import br.com.karol.sistema.business.service.UserDetailsServiceImpl;
import br.com.karol.sistema.business.service.UsuarioService;
import br.com.karol.sistema.constants.TestConstants;
import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.infra.repository.UsuarioRepository;
import br.com.karol.sistema.infra.security.SecurityConfig;
import br.com.karol.sistema.unit.utils.ControllerTestUtils;
import br.com.karol.sistema.unit.utils.UsuarioUtils;

@AutoConfigureJsonTesters
@Import(SecurityConfig.class)
@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerUnitTest {

    private final static String BASE_URL = "/auth";
    
    private final static Usuario DEFAULT_USER = UsuarioUtils.getUsuario();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private UsuarioService usuarioService;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private UsuarioRepository usuarioRepository;
    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JacksonTester<AuthenticationDTO> authenticationDTOJson;


    @Test
    void testFazerLogin() throws IOException, Exception {
        // arrange
        var requestBody = new AuthenticationDTO(TestConstants.LOGIN, TestConstants.SENHA);

        Usuario user = DEFAULT_USER;
        when(usuarioService.autenticarUsuario(anyString(), anyString())).thenReturn(user);
        when(tokenService.generateToken(any())).thenReturn("user-token");
        
        // act
        ControllerTestUtils.postRequest(mvc, BASE_URL, authenticationDTOJson.write(requestBody).getJson())

        // assert
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").exists());
    }
    @Test
    void testFazerLoginComBodyInvalido() throws IOException, Exception {
        // arrange
        var requestBody = new AuthenticationDTO(null, null);
        
        // act
        ControllerTestUtils.postRequest(mvc, BASE_URL, authenticationDTOJson.write(requestBody).getJson())

        // assert
            .andExpect(status().isBadRequest());
    }
}