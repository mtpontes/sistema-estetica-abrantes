package br.com.karol.sistema.unit.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import br.com.karol.sistema.api.controller.ClienteController;
import br.com.karol.sistema.api.dto.EnderecoDTO;
import br.com.karol.sistema.api.dto.cliente.AtualizarClienteDTO;
import br.com.karol.sistema.api.dto.cliente.CriarClienteDTO;
import br.com.karol.sistema.api.dto.cliente.DadosClienteDTO;
import br.com.karol.sistema.api.dto.cliente.DadosCompletosClienteDTO;
import br.com.karol.sistema.business.service.ClienteService;
import br.com.karol.sistema.business.service.TokenService;
import br.com.karol.sistema.config.ContextualizeUsuarioTypeWithRoles;
import br.com.karol.sistema.constants.TestConstants;
import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.infra.repository.UsuarioRepository;
import br.com.karol.sistema.infra.security.SecurityConfig;
import br.com.karol.sistema.unit.utils.ClienteUtils;
import br.com.karol.sistema.unit.utils.ControllerTestUtils;

@AutoConfigureJsonTesters
@Import(SecurityConfig.class)
@WebMvcTest(ClienteController.class)
public class ClienteControllerUnitTest {

    private static final String BASE_URL = "/clientes";
    private static final String ME_ROUTE = BASE_URL + "/me";

    private static final Cliente CLIENTE_DEFAULT = ClienteUtils.getCliente();


    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClienteService service;

    @Autowired
    private JacksonTester<CriarClienteDTO> criarClienteDTO;
    @Autowired
    private JacksonTester<AtualizarClienteDTO> atualizarClienteDTOJson;
    @Autowired
    private JacksonTester<EnderecoDTO> enderecoDTOJson;

    // Dependências SecurityFilter
    @MockBean
    private TokenService tokenService;
    @MockBean
    private UsuarioRepository usuarioRepository;


    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"USER", "ADMIN"})
    void testCriarClienteComRolesAutorizadas() throws IOException, Exception {
        // arrange
        var requestBody = new CriarClienteDTO(
            CLIENTE_DEFAULT.getCpf(),
            CLIENTE_DEFAULT.getNome(),
            CLIENTE_DEFAULT.getTelefone(),
            CLIENTE_DEFAULT.getEmail(),
            new EnderecoDTO(CLIENTE_DEFAULT.getEndereco())
        );
        
        var responseBody = new DadosCompletosClienteDTO(CLIENTE_DEFAULT);
        when(service.salvarCliente(any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.postRequest(mvc, BASE_URL, criarClienteDTO.write(requestBody).getJson())
            // assert
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.cpf").exists())
            .andExpect(jsonPath("$.nome").exists())
            .andExpect(jsonPath("$.telefone").exists())
            .andExpect(jsonPath("$.email").exists())
            .andExpect(jsonPath("$.endereco").exists())
            .andExpect(jsonPath("$.endereco.rua").exists())
            .andExpect(jsonPath("$.endereco.numero").exists())
            .andExpect(jsonPath("$.endereco.cidade").exists())
            .andExpect(jsonPath("$.endereco.bairro").exists())
            .andExpect(jsonPath("$.endereco.estado").exists());

        verify(service).salvarCliente(any());
    }
    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"CLIENT"})
    void testCriarClienteComRolesNaoAutorizadas() throws IOException, Exception {
        // arrange
        var requestBody = new CriarClienteDTO(
            CLIENTE_DEFAULT.getCpf(),
            CLIENTE_DEFAULT.getNome(),
            CLIENTE_DEFAULT.getTelefone(),
            CLIENTE_DEFAULT.getEmail(),
            new EnderecoDTO(CLIENTE_DEFAULT.getEndereco())
        );
        
        var responseBody = new DadosCompletosClienteDTO(CLIENTE_DEFAULT);
        when(service.salvarCliente(any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.postRequest(mvc, BASE_URL, criarClienteDTO.write(requestBody).getJson())
            // assert
            .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }
    @Test
    @WithMockUser
    void testCriarClienteComBodyInvalido() throws IOException, Exception {
        // arrange
        var requestBody = new CriarClienteDTO(
            TestConstants.CPF_MUITO_PEQUENO, // menos de 11 caracteres
            null,
            TestConstants.TELEFONE_MUITO_PEQUENO,
            null,
            null
        );
        
        var responseBody = new DadosCompletosClienteDTO(CLIENTE_DEFAULT);
        when(service.salvarCliente(any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.postRequest(mvc, BASE_URL, criarClienteDTO.write(requestBody).getJson())
            // assert
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.fields.cpf").exists())
            .andExpect(jsonPath("$.fields.nome").exists())
            .andExpect(jsonPath("$.fields.telefone").exists())
            .andExpect(jsonPath("$.fields.email").exists())
            .andExpect(jsonPath("$.fields.endereco").exists());

        verifyNoInteractions(service);
    }

    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"USER", "ADMIN"})
    void testListarClientesComRolesAutorizadas() throws IOException, Exception {
        // arrange
        var responseBody = new PageImpl<>(List.of(new DadosClienteDTO(CLIENTE_DEFAULT))); 
        when(service.listarTodosClientes(any(), any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.getRequest(mvc, BASE_URL)
            // assert
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0]id").exists())
            .andExpect(jsonPath("$.content[0]cpf").exists())
            .andExpect(jsonPath("$.content[0]nome").exists());
    }
    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"CLIENT"})
    void testListarClientesComRolesNaoAutorizadas() throws IOException, Exception {
        // act
        ControllerTestUtils.getRequest(mvc, BASE_URL)
            // assert
            .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }
    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"USER", "ADMIN"})
    void testListarClientesComQueryParams() throws IOException, Exception {
        // arrange
        var PARAM_NOME = "any";
        var responseBody = new PageImpl<>(List.of(new DadosClienteDTO(CLIENTE_DEFAULT))); 
        when(service.listarTodosClientes(eq(PARAM_NOME), any(Pageable.class))).thenReturn(responseBody);

        // act
        mvc.perform(get(BASE_URL)
            .param("nome", PARAM_NOME)
        )
        // assert
        .andExpect(status().isOk());

        verify(service).listarTodosClientes(eq(PARAM_NOME), any(Pageable.class));
    }

    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"USER", "ADMIN"})
    void testBuscarClienteComRolesAutorizadas() throws IOException, Exception {
        // arrange
        var responseBody = new DadosCompletosClienteDTO(CLIENTE_DEFAULT);
        when(service.buscarClientePorId(any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.getRequest(mvc, BASE_URL.concat("/1"))
            // assert
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.cpf").exists())
            .andExpect(jsonPath("$.nome").exists())
            .andExpect(jsonPath("$.telefone").exists())
            .andExpect(jsonPath("$.email").exists())
            .andExpect(jsonPath("$.endereco").exists());
    }
    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"CLIENT"})
    void testBuscarClienteComRolesNaoAutorizadas() throws IOException, Exception {
        // arrange
        var responseBody = new DadosCompletosClienteDTO(CLIENTE_DEFAULT);
        when(service.buscarClientePorId(any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.getRequest(mvc, BASE_URL.concat("/1"))
            // assert
            .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }

    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"USER", "ADMIN"})
    void testAtualizarClienteComRolesAutorizadas() throws IOException, Exception {
        // arrange
        var requestBody = new CriarClienteDTO(
            CLIENTE_DEFAULT.getCpf(),
            CLIENTE_DEFAULT.getNome(),
            CLIENTE_DEFAULT.getTelefone(),
            CLIENTE_DEFAULT.getEmail(),
            new EnderecoDTO(CLIENTE_DEFAULT.getEndereco())
        );
        
        var responseBody = new DadosCompletosClienteDTO(CLIENTE_DEFAULT);
        when(service.salvarCliente(any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.postRequest(mvc, BASE_URL, criarClienteDTO.write(requestBody).getJson())
            // assert
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.cpf").exists())
            .andExpect(jsonPath("$.nome").exists())
            .andExpect(jsonPath("$.telefone").exists())
            .andExpect(jsonPath("$.email").exists())
            .andExpect(jsonPath("$.endereco").exists())
            .andExpect(jsonPath("$.endereco.rua").exists())
            .andExpect(jsonPath("$.endereco.numero").exists())
            .andExpect(jsonPath("$.endereco.cidade").exists())
            .andExpect(jsonPath("$.endereco.bairro").exists())
            .andExpect(jsonPath("$.endereco.estado").exists());

        verify(service).salvarCliente(any());
    }
    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"CLIENT"})
    void testAtualizarClienteComRolesNaoAutorizadas() throws IOException, Exception {
        // arrange
        var requestBody = new AtualizarClienteDTO(
            CLIENTE_DEFAULT.getNome(),
            CLIENTE_DEFAULT.getTelefone(),
            CLIENTE_DEFAULT.getEmail()
        );

        // act
        ControllerTestUtils.putRequest(mvc, BASE_URL, atualizarClienteDTOJson.write(requestBody).getJson())
            // assert
            .andExpect(status().isForbidden());
    }

    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"USER", "ADMIN"})
    void testAtualizarEnderecoClienteComRolesAutorizadas() throws IOException, Exception {
        // arrange
        var requestBody = new EnderecoDTO(CLIENTE_DEFAULT.getEndereco());
        var responseBody = new DadosCompletosClienteDTO(CLIENTE_DEFAULT);
        when(service.editarEnderecoCliente(anyLong(), any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.putRequest(mvc, (BASE_URL.concat("/1/endereco")), enderecoDTOJson.write(requestBody).getJson())
            // assert
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.cpf").exists())
            .andExpect(jsonPath("$.nome").exists())
            .andExpect(jsonPath("$.telefone").exists())
            .andExpect(jsonPath("$.email").exists())
            .andExpect(jsonPath("$.endereco").exists())
            .andExpect(jsonPath("$.endereco.rua").exists())
            .andExpect(jsonPath("$.endereco.numero").exists())
            .andExpect(jsonPath("$.endereco.cidade").exists())
            .andExpect(jsonPath("$.endereco.bairro").exists())
            .andExpect(jsonPath("$.endereco.estado").exists());

        verify(service).editarEnderecoCliente(anyLong(), any());
    }
    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"CLIENT"})
    void testAtualizarEnderecoClienteComRolesNaoAutorizadas() throws IOException, Exception {
        // arrange
        var requestBody = new AtualizarClienteDTO(
            CLIENTE_DEFAULT.getNome(),
            CLIENTE_DEFAULT.getTelefone(),
            CLIENTE_DEFAULT.getEmail()
        );

        // act
        ControllerTestUtils.putRequest(mvc, BASE_URL, atualizarClienteDTOJson.write(requestBody).getJson())
            // assert
            .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser
    void testAtualizarEnderecoClienteComBodyInvalido() throws IOException, Exception {
        // arrange
        var requestBody = new EnderecoDTO();
        var responseBody = new DadosCompletosClienteDTO(CLIENTE_DEFAULT);
        when(service.editarEnderecoCliente(anyLong(), any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.putRequest(mvc, (BASE_URL.concat("/1/endereco")), enderecoDTOJson.write(requestBody).getJson())
            // assert
            .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }

    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"USER", "ADMIN"})
    void testDeletarClienteComRolesAutorizadas() throws IOException, Exception {
        // act
        ControllerTestUtils.deleteMapping(mvc, BASE_URL.concat("/1"))
            // assert
            .andExpect(status().isNoContent());
    }
    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"CLIENT"})
    void testDeletarClienteComRolesNaoAutorizadas() throws IOException, Exception {
        // act
        ControllerTestUtils.deleteMapping(mvc, BASE_URL.concat("/1"))
            // assert
            .andExpect(status().isForbidden());
    }

    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"CLIENT"})
    void testBuscarClienteMeComRolesAutorizadas() throws IOException, Exception {
        // arrange
        var responseBody = CLIENTE_DEFAULT;
        when(service.getClienteByUsuarioId(any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.getRequest(mvc, ME_ROUTE)
            // assert
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.cpf").exists())
            .andExpect(jsonPath("$.nome").exists())
            .andExpect(jsonPath("$.telefone").exists())
            .andExpect(jsonPath("$.email").exists())
            .andExpect(jsonPath("$.endereco").exists());
    }
    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"USER", "ADMIN"})
    void testBuscarClienteMeComRolesNaoAutorizadas() throws IOException, Exception {
        // act
        ControllerTestUtils.getRequest(mvc, ME_ROUTE)
            // assert
            .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }

    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"CLIENT"})
    void atualizarClienteMeComRolesAutorizadas() throws IOException, Exception {
        // arrange
        var requestBody = new AtualizarClienteDTO(
            CLIENTE_DEFAULT.getNome(),
            CLIENTE_DEFAULT.getTelefone(),
            CLIENTE_DEFAULT.getEmail()
        );
        
        var responseBody = new DadosCompletosClienteDTO(CLIENTE_DEFAULT);
        when(service.getClienteByUsuarioId(any())).thenReturn(CLIENTE_DEFAULT);
        when(service.editarContatoCliente(any(Cliente.class), any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.putRequest(mvc, ME_ROUTE, atualizarClienteDTOJson.write(requestBody).getJson())
            // assert
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.cpf").exists())
            .andExpect(jsonPath("$.nome").exists())
            .andExpect(jsonPath("$.telefone").exists())
            .andExpect(jsonPath("$.email").exists())
            .andExpect(jsonPath("$.endereco").exists())
            .andExpect(jsonPath("$.endereco.rua").exists())
            .andExpect(jsonPath("$.endereco.numero").exists())
            .andExpect(jsonPath("$.endereco.cidade").exists())
            .andExpect(jsonPath("$.endereco.bairro").exists())
            .andExpect(jsonPath("$.endereco.estado").exists());
    }
    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"USER", "ADMIN"})
    void testAtualizarClienteMeComRolesNaoAutorizadas() throws IOException, Exception {
        // arrange
        var requestBody = new AtualizarClienteDTO(
            CLIENTE_DEFAULT.getNome(),
            CLIENTE_DEFAULT.getTelefone(),
            CLIENTE_DEFAULT.getEmail()
        );

        // act
        ControllerTestUtils.putRequest(mvc, ME_ROUTE, atualizarClienteDTOJson.write(requestBody).getJson())
            // assert
            .andExpect(status().isForbidden());
    }

    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"CLIENT"})
    void testAtualizarEnderecoClienteMeComRolesAutorizadas() throws IOException, Exception {
        // arrange
        var requestBody = new EnderecoDTO(CLIENTE_DEFAULT.getEndereco());
        var responseBody = new DadosCompletosClienteDTO(CLIENTE_DEFAULT);
        when(service.getClienteByUsuarioId(anyLong())).thenReturn(CLIENTE_DEFAULT);
        when(service.editarEnderecoCliente(any(Cliente.class), any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.putRequest(
            mvc, 
            (ME_ROUTE.concat("/endereco")), 
            enderecoDTOJson.write(requestBody).getJson()
        )
        // assert
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.cpf").exists())
        .andExpect(jsonPath("$.nome").exists())
        .andExpect(jsonPath("$.telefone").exists())
        .andExpect(jsonPath("$.email").exists())
        .andExpect(jsonPath("$.endereco").exists())
        .andExpect(jsonPath("$.endereco.rua").exists())
        .andExpect(jsonPath("$.endereco.numero").exists())
        .andExpect(jsonPath("$.endereco.cidade").exists())
        .andExpect(jsonPath("$.endereco.bairro").exists())
        .andExpect(jsonPath("$.endereco.estado").exists());
    }
    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"USER", "ADMIN"})
    void testAtualizarEnderecoClienteMeComRolesNaoAutorizadas() throws IOException, Exception {
        // arrange
        var requestBody = new AtualizarClienteDTO(
            CLIENTE_DEFAULT.getNome(),
            CLIENTE_DEFAULT.getTelefone(),
            CLIENTE_DEFAULT.getEmail()
        );

        // act
        ControllerTestUtils.putRequest(mvc, ME_ROUTE.concat("/endereco"), atualizarClienteDTOJson.write(requestBody).getJson())
            // assert
            .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }
    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"CLIENT"})
    void testAtualizarEnderecoClienteMeComBodyInvalido() throws IOException, Exception {
        // arrange
        var requestBody = new EnderecoDTO();
        var responseBody = new DadosCompletosClienteDTO(CLIENTE_DEFAULT);
        when(service.getClienteByUsuarioId(anyLong())).thenReturn(CLIENTE_DEFAULT);
        when(service.editarEnderecoCliente(anyLong(), any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.putRequest(mvc, (ME_ROUTE.concat("/endereco")), enderecoDTOJson.write(requestBody).getJson())
            // assert
            .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }
}