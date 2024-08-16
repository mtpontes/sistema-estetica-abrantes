package br.com.karol.sistema.unit.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import br.com.karol.sistema.api.controller.UsuarioController;
import br.com.karol.sistema.api.dto.usuario.AtualizarNomeUsuarioDTO;
import br.com.karol.sistema.api.dto.usuario.AtualizarSenhaOutroUsuarioDTO;
import br.com.karol.sistema.api.dto.usuario.AtualizarSenhaUsuarioDTO;
import br.com.karol.sistema.api.dto.usuario.CriarUsuarioDTO;
import br.com.karol.sistema.api.dto.usuario.DadosUsuarioDTO;
import br.com.karol.sistema.builder.UsuarioFactory;
import br.com.karol.sistema.business.service.ClienteService;
import br.com.karol.sistema.business.service.ProcedimentoService;
import br.com.karol.sistema.business.service.TokenService;
import br.com.karol.sistema.business.service.UserDetailsServiceImpl;
import br.com.karol.sistema.business.service.UsuarioService;
import br.com.karol.sistema.constants.TestConstants;
import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.infra.repository.UsuarioRepository;
import br.com.karol.sistema.infra.security.SecurityConfig;
import br.com.karol.sistema.utils.ControllerTestUtils;

@AutoConfigureJsonTesters
@Import(SecurityConfig.class)
@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    private final static String BASE_URL = "/usuarios";
    private final static String ADMIN_ROUTE = BASE_URL + "/admin";

    private final static Usuario DEFAULT_USUARIO = UsuarioFactory.getUsuario();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UsuarioService service;
    @MockBean
    private ClienteService clienteService;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private ProcedimentoService procedimentoService;

    // Dependências do SecurityFilter
    @MockBean
    private UsuarioRepository repository;
    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;


    @Autowired
    private JacksonTester<CriarUsuarioDTO> criarUsuarioDTOJson;
    @Autowired
    private JacksonTester<AtualizarNomeUsuarioDTO> atualizarUsuarioDTOJson;
    @Autowired
    private JacksonTester<AtualizarSenhaUsuarioDTO> atualizarSenhaUsuarioDTOJson;
    @Autowired
    private JacksonTester<AtualizarSenhaOutroUsuarioDTO> atualizarSenhaOutroUsuarioDTOJson;


    @Test
    @WithMockUser(roles = "ADMIN")
    void testCriarUsuario() throws IOException, Exception {
        // arrange
        CriarUsuarioDTO requestBody = new CriarUsuarioDTO(
            DEFAULT_USUARIO.getNome(),
            DEFAULT_USUARIO.getLogin(),
            DEFAULT_USUARIO.getSenha()
        );

        var responseBody = new DadosUsuarioDTO(DEFAULT_USUARIO);
        when(service.salvarUsuario(any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.postRequest(mvc, BASE_URL, criarUsuarioDTOJson.write(requestBody).getJson())
        
        // assert
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.nome").exists())
            .andExpect(jsonPath("$.role").exists());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void testCriarUsuario_comBodyInvalido01() throws IOException, Exception {
        // arrange
        CriarUsuarioDTO requestBody = new CriarUsuarioDTO(
            TestConstants.NOME_VAZIO,
            TestConstants.LOGIN_MUITO_PEQUENO,
            TestConstants.SENHA_MUITO_PEQUENA
        );

        // act
        ControllerTestUtils.postRequest(mvc, BASE_URL, criarUsuarioDTOJson.write(requestBody).getJson())

        // assert
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").exists())
            .andExpect(jsonPath("$.fields").exists())
            .andExpect(jsonPath("$.fields.nome").exists())
            .andExpect(jsonPath("$.fields.login").exists())
            .andExpect(jsonPath("$.fields.senha").exists());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void testCriarUsuario_comBodyInvalido02() throws IOException, Exception {
        // arrange
        CriarUsuarioDTO requestBody = new CriarUsuarioDTO(
            TestConstants.NOME_VAZIO,
            TestConstants.LOGIN_MUITO_GRANDE,
            TestConstants.SENHA_MUITO_GRANDE
        );

        // act
        ControllerTestUtils.postRequest(mvc, BASE_URL, criarUsuarioDTOJson.write(requestBody).getJson())

        // assert
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").exists())
            .andExpect(jsonPath("$.fields").exists())
            .andExpect(jsonPath("$.fields.nome").exists())
            .andExpect(jsonPath("$.fields.login").exists())
            .andExpect(jsonPath("$.fields.senha").exists());
    }
    @ParameterizedTest
    @CsvSource(value = {"USER", "CLIENT"})
    void testCriarUsuario_comRolesNaoAutorizadas(String role) throws IOException, Exception {
        // arrange
        ControllerTestUtils.withMockUserManual(role);
        
        CriarUsuarioDTO requestBody = new CriarUsuarioDTO(
            DEFAULT_USUARIO.getNome(),
            DEFAULT_USUARIO.getLogin(),
            DEFAULT_USUARIO.getSenha()
        );

        // act
        ControllerTestUtils.postRequest(mvc, BASE_URL, criarUsuarioDTOJson.write(requestBody).getJson())

        // assert
            .andExpect(status().isForbidden());
    }


    @ParameterizedTest
    @CsvSource(value = {"USER", "CLIENT", "ADMIN"})
    void testBuscarUsuarioPorAutenticacao(String role) throws IOException, Exception {
        // arrange
        ControllerTestUtils.withMockUserManual(role);

        var responseBody = new DadosUsuarioDTO(DEFAULT_USUARIO);
        when(service.getDadosUsuarioAtual(any())).thenReturn(responseBody);
        
        // act
        ControllerTestUtils.getRequest(mvc, BASE_URL)

        // assert
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.nome").exists())
            .andExpect(jsonPath("$.role").exists());
    }

    @ParameterizedTest
    @CsvSource(value = {"USER", "CLIENT", "ADMIN"})
    void testAtualizarUsuarioAtual_comRolesAutorizadas(String role) throws IOException, Exception {
        // arrange
        var requestBody = new AtualizarNomeUsuarioDTO(DEFAULT_USUARIO.getNome());

        ControllerTestUtils.withMockUserManual(role);
        when(service.atualizarNomeUsuarioAtual(any(), any())).thenReturn(new DadosUsuarioDTO(DEFAULT_USUARIO));
        
        // act
        ControllerTestUtils.patchRequest(
            mvc, 
            (BASE_URL + "/nome"), 
            atualizarUsuarioDTOJson.write(requestBody).getJson()
        )
        // assert
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.nome").exists())
        .andExpect(jsonPath("$.role").exists());
    }
    @Test
    @WithMockUser
    void testAtualizarUsuarioAtual_comBodyInvalido() throws IOException, Exception {
        // arrange
        var requestBody = new AtualizarNomeUsuarioDTO("");

        // act
        ControllerTestUtils.patchRequest(
            mvc, 
            (BASE_URL + "/nome"), 
            atualizarUsuarioDTOJson.write(requestBody).getJson()
        )
        // assert
        .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }

    @ParameterizedTest
    @CsvSource(value = {"USER", "CLIENT", "ADMIN"})
    void testAtualizarSenhaUsuarioAtual_comRolesAutorizadas(String role) throws IOException, Exception {
        // arrange
        ControllerTestUtils.withMockUserManual(role);
        Usuario currentContextUser = (Usuario) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();
        when(service.atualizarSenhaUsuarioAtual(any(), any())).thenReturn(currentContextUser);
        
        var requestBody = new AtualizarSenhaUsuarioDTO(
            DEFAULT_USUARIO.getLogin(), 
            DEFAULT_USUARIO.getSenha()
        );

        var responseBody = "novo-token";
        when(tokenService.generateToken(anyString())).thenReturn(responseBody);
        
        // act
        ControllerTestUtils.patchRequest(
            mvc, 
            (BASE_URL + "/senha"), 
            atualizarSenhaUsuarioDTOJson.write(requestBody).getJson()
        )
        // assert
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").value(responseBody));

        verify(service).atualizarSenhaUsuarioAtual(
            any(currentContextUser.getClass()), 
            any(requestBody.getClass()));
    }

    @Test
    @WithMockUser
    void testAtualizarSenhaUsuarioAtual_comBodyInvalido() throws IOException, Exception {
        // arrange
        var requestBody = new AtualizarSenhaUsuarioDTO("", "");

        // act
        ControllerTestUtils.patchRequest(
            mvc, 
            (BASE_URL + "/senha"), 
            atualizarSenhaUsuarioDTOJson.write(requestBody).getJson()
        )
        // assert
        .andExpect(status().isBadRequest());
    }

    // Rotas de ADMIN: /usuarios/admin
    @Test
    @WithMockUser(roles = "ADMIN")
    void testAdminCriarUsuario() throws IOException, Exception {
        // arrange
        CriarUsuarioDTO requestBody = new CriarUsuarioDTO(
            DEFAULT_USUARIO.getNome(),
            DEFAULT_USUARIO.getLogin(),
            DEFAULT_USUARIO.getSenha()
        );

        var responseBody = new DadosUsuarioDTO(DEFAULT_USUARIO);
        when(service.adminSalvarUsuario(any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.postRequest(
            mvc, 
            ADMIN_ROUTE, 
            criarUsuarioDTOJson.write(requestBody).getJson())
        
        // assert
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.nome").exists())
            .andExpect(jsonPath("$.role").exists());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void testAdminCriarUsuario_comBodyInvalido() throws IOException, Exception {
        // arrange
        CriarUsuarioDTO requestBody = new CriarUsuarioDTO(
            null,
            null,
            null
        );

        // act
        ControllerTestUtils.postRequest(
            mvc, 
            ADMIN_ROUTE, 
            criarUsuarioDTOJson.write(requestBody).getJson()
        )
        
        // assert
        .andExpect(status().isBadRequest());
    }
    @ParameterizedTest
    @CsvSource(value = {"USER", "CLIENT"})
    void testAdminCriarUsuario_comRolesNaoAutorizadas(String role) throws IOException, Exception {
        // arrange
        ControllerTestUtils.withMockUserManual(role);
        
        CriarUsuarioDTO requestBody = new CriarUsuarioDTO(
            DEFAULT_USUARIO.getNome(),
            DEFAULT_USUARIO.getLogin(),
            DEFAULT_USUARIO.getSenha()
        );

        // act
        ControllerTestUtils.postRequest(
            mvc, 
            ADMIN_ROUTE, 
            criarUsuarioDTOJson.write(requestBody).getJson()
        )

        // assert
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testAdminBuscarUsuario() throws IOException, Exception {
        // arrange
        var responseBody = List.of(new DadosUsuarioDTO(DEFAULT_USUARIO));
        when(service.adminListarTodosUsuarios()).thenReturn(responseBody);
        
        // act
        ControllerTestUtils.getRequest(mvc, ADMIN_ROUTE)

        // assert
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0]id").exists())
            .andExpect(jsonPath("$.[0]nome").exists())
            .andExpect(jsonPath("$.[0]role").exists());
    }
    @ParameterizedTest
    @CsvSource(value = {"USER", "CLIENT"})
    void testAdminBuscarUsuario(String role) throws IOException, Exception {
        // arrange
        ControllerTestUtils.withMockUserManual(role);
        
        // act
        ControllerTestUtils.getRequest(mvc, ADMIN_ROUTE + "/1")

        // assert
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testAdminAtualizarSenhaUsuario() throws IOException, Exception {
        // arrange
        var requestBody = new AtualizarSenhaOutroUsuarioDTO(DEFAULT_USUARIO.getSenha());

        var responseBody = new DadosUsuarioDTO(DEFAULT_USUARIO);
        when(service.adminAtualizarSenhaOutrosUsuarios(any(), any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.patchRequest(
            mvc, 
            (ADMIN_ROUTE + "/1"), 
            atualizarSenhaOutroUsuarioDTOJson.write(requestBody).getJson()
        )
        // assert
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.nome").exists())
        .andExpect(jsonPath("$.role").exists());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void testAdminAtualizarSenhaUsuario_comBodyInvalido() throws IOException, Exception {
        // arrange
        var requestBody = new AtualizarSenhaOutroUsuarioDTO(null);

        var responseBody = new DadosUsuarioDTO(DEFAULT_USUARIO);
        when(service.adminAtualizarSenhaOutrosUsuarios(any(), any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.patchRequest(
            mvc, 
            (ADMIN_ROUTE + "/1"), 
            atualizarSenhaOutroUsuarioDTOJson.write(requestBody).getJson()
        )
        // assert
        .andExpect(status().isBadRequest());
    }
    @ParameterizedTest
    @CsvSource(value = {"USER", "CLIENT"})
    void testAdminAtualizarSenhaUsuario_comRolesNaoAutorizadas(String role) throws IOException, Exception {
        // arrange
        ControllerTestUtils.withMockUserManual(role);
        var requestBody = new AtualizarSenhaUsuarioDTO(DEFAULT_USUARIO.getLogin(), DEFAULT_USUARIO.getSenha());
        
        // act
        ControllerTestUtils.patchRequest(
            mvc, 
            (ADMIN_ROUTE + "/1"), 
            atualizarSenhaUsuarioDTOJson.write(requestBody).getJson()
        )
        // assert
        .andExpect(status().isForbidden());
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeletarUsuario() throws Exception {
        ControllerTestUtils.deleteMapping(mvc, ADMIN_ROUTE + "/1")
            .andExpect(status().isNoContent());
    }
    @ParameterizedTest
    @CsvSource(value = {"USER", "CLIENT"})
    void testDeletarUsuario_comRolesNaoAutorizadas(String role) throws IOException, Exception {
        // arrange
        ControllerTestUtils.withMockUserManual(role);
        
        // act
        ControllerTestUtils.deleteMapping(mvc, (ADMIN_ROUTE + "/1"))

        // assert
            .andExpect(status().isForbidden());
    }
}