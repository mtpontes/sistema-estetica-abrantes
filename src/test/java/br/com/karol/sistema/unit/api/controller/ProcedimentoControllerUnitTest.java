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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import br.com.karol.sistema.api.controller.ProcedimentoController;
import br.com.karol.sistema.api.dto.procedimento.AtualizarProcedimentoDTO;
import br.com.karol.sistema.api.dto.procedimento.CriarProcedimentoDTO;
import br.com.karol.sistema.api.dto.procedimento.DadosProcedimentoDTO;
import br.com.karol.sistema.api.mapper.ProcedimentoMapper;
import br.com.karol.sistema.business.service.ProcedimentoService;
import br.com.karol.sistema.business.service.TokenService;
import br.com.karol.sistema.config.ContextualizeUsuarioTypeWithRoles;
import br.com.karol.sistema.domain.Procedimento;
import br.com.karol.sistema.infra.repository.AgendamentoRepository;
import br.com.karol.sistema.infra.repository.ProcedimentoRepository;
import br.com.karol.sistema.infra.repository.UsuarioRepository;
import br.com.karol.sistema.infra.security.SecurityConfig;
import br.com.karol.sistema.unit.utils.ControllerTestUtils;
import br.com.karol.sistema.unit.utils.ProcedimentoUtils;

@AutoConfigureJsonTesters
@Import(SecurityConfig.class)
@WebMvcTest(ProcedimentoController.class)
public class ProcedimentoControllerUnitTest {

    private final static String BASE_URL = "/procedimentos";
    
    private final static Procedimento DEFAULT_PROCEDIMENTO = ProcedimentoUtils.getProcedimento();

    @MockBean
    private ProcedimentoService service;
    @MockBean
    private ProcedimentoRepository repository; 
    @MockBean
    private ProcedimentoMapper mapper;
    @MockBean
    private AgendamentoRepository agendamentoRepository;

    // dependências do SecurityFilter
    @MockBean
    private TokenService tokenService;
    @MockBean
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JacksonTester<CriarProcedimentoDTO> criarProcedimentoDTOJson;
    @Autowired
    private JacksonTester<AtualizarProcedimentoDTO> atualizarProcedimentoDTOJson;

    @Autowired
    private MockMvc mvc;


    @Test
    @WithMockUser(roles = "ADMIN")
    void testCriarProcedimento() throws Exception {
        // arrange
        var requestBody = new CriarProcedimentoDTO(
            DEFAULT_PROCEDIMENTO.getNome(),
            DEFAULT_PROCEDIMENTO.getDescricao(),
            DEFAULT_PROCEDIMENTO.getDuracao(),
            DEFAULT_PROCEDIMENTO.getValor()
        );

        var responseBody = new DadosProcedimentoDTO(DEFAULT_PROCEDIMENTO);
        when(service.salvarProcedimento(any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.postRequest(mvc, BASE_URL, criarProcedimentoDTOJson.write(requestBody).getJson())

            // assert
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.nome").exists())
            .andExpect(jsonPath("$.descricao").exists())
            .andExpect(jsonPath("$.duracao").exists())
            .andExpect(jsonPath("$.valor").exists());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void testCriarProcedimentoComBodyInvalido() throws Exception {
        // arrange
        var requestBody = new CriarProcedimentoDTO(
            null,
            null,
            null,
            49.00 // valor menor que 50
        );

        var responseBody = new DadosProcedimentoDTO(DEFAULT_PROCEDIMENTO);
        when(service.salvarProcedimento(any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.postRequest(mvc, BASE_URL, criarProcedimentoDTOJson.write(requestBody).getJson())

            // assert
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").exists())
            .andExpect(jsonPath("$.fields").exists())
            .andExpect(jsonPath("$.fields.nome").exists())
            .andExpect(jsonPath("$.fields.descricao").exists())
            .andExpect(jsonPath("$.fields.duracao").exists())
            .andExpect(jsonPath("$.fields.valor").exists());

        verifyNoInteractions(service);
    }
    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"USER", "CLIENT"})
    void testCriarProcedimentoComRolesNaoAutorizadas() throws Exception {
        // arrange
        var requestBody = new CriarProcedimentoDTO(
            DEFAULT_PROCEDIMENTO.getNome(),
            DEFAULT_PROCEDIMENTO.getDescricao(),
            DEFAULT_PROCEDIMENTO.getDuracao(),
            DEFAULT_PROCEDIMENTO.getValor()
        );

        // act
        ControllerTestUtils.postRequest(mvc, BASE_URL, criarProcedimentoDTOJson.write(requestBody).getJson())

            // assert
            .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }

    @Test
    void testListarProcedimentos() throws Exception {
        // arrange
        var responseBody = new PageImpl<>(List.of(new DadosProcedimentoDTO(DEFAULT_PROCEDIMENTO)));
        when(service.listarTodosProcedimentos(any(), any(), any(), any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.getRequest(mvc, BASE_URL)

            // assert
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0]id").exists())
            .andExpect(jsonPath("$.content[0]nome").exists())
            .andExpect(jsonPath("$.content[0]descricao").exists())
            .andExpect(jsonPath("$.content[0]duracao").exists())
            .andExpect(jsonPath("$.content[0]valor").exists());
    }
    @Test
    void testListarProcedimentosComQueryParams() throws Exception {
        // arrange
        var PARAM_NOME = "Nome";
        var PARAM_VALOR_MIN = Double.valueOf("51.00");
        var PARAM_VALOR_MAX = Double.valueOf("60.00");

        var responseBody = new PageImpl<>(List.of(new DadosProcedimentoDTO(DEFAULT_PROCEDIMENTO)));
        when(service.listarTodosProcedimentos(
            eq(PARAM_NOME), 
            eq(PARAM_VALOR_MIN), 
            eq(PARAM_VALOR_MAX), 
            any(Pageable.class)
            )
        ).thenReturn(responseBody);

        // act
        mvc.perform(get(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .param("nome", PARAM_NOME)
            .param("valorMin", PARAM_VALOR_MIN.toString())
            .param("valorMax", PARAM_VALOR_MAX.toString())
            )
            .andExpect(status().isOk());
        
        verify(service).listarTodosProcedimentos(
            eq(PARAM_NOME), 
            eq(PARAM_VALOR_MIN), 
            eq(PARAM_VALOR_MAX), 
            any(Pageable.class));
    }

    @Test
    void testBuscarProcedimento() throws Exception {
        // arrange
        var responseBody = new DadosProcedimentoDTO(DEFAULT_PROCEDIMENTO);
        when(service.mostrarProcedimento(anyLong())).thenReturn(responseBody);

        // act
        ControllerTestUtils.getRequest(mvc, (BASE_URL + "/1"))

            // assert
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.nome").exists())
            .andExpect(jsonPath("$.descricao").exists())
            .andExpect(jsonPath("$.duracao").exists())
            .andExpect(jsonPath("$.valor").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testAtualizarProcedimento() throws Exception {
        // arrange
        var requestBody = new AtualizarProcedimentoDTO(
            DEFAULT_PROCEDIMENTO.getNome(),
            DEFAULT_PROCEDIMENTO.getDescricao(),
            DEFAULT_PROCEDIMENTO.getDuracao(),
            DEFAULT_PROCEDIMENTO.getValor()
        );

        var responseBody = new DadosProcedimentoDTO(DEFAULT_PROCEDIMENTO);
        when(service.editarProcedimento(anyLong(), any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.putRequest(mvc, (BASE_URL + "/1"), atualizarProcedimentoDTOJson.write(requestBody).getJson())
            
            // assert
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.nome").exists())
            .andExpect(jsonPath("$.descricao").exists())
            .andExpect(jsonPath("$.duracao").exists())
            .andExpect(jsonPath("$.valor").exists());
    }
    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"USER", "CLIENT"})
    void testAtualizarProcedimentoComRolesNaoAutorizadas() throws Exception {
        // arrange
        var requestBody = new AtualizarProcedimentoDTO(
            DEFAULT_PROCEDIMENTO.getNome(),
            DEFAULT_PROCEDIMENTO.getDescricao(),
            DEFAULT_PROCEDIMENTO.getDuracao(),
            DEFAULT_PROCEDIMENTO.getValor()
        );

        // act
        ControllerTestUtils.putRequest(mvc, (BASE_URL + "/1"), atualizarProcedimentoDTOJson.write(requestBody).getJson())
        
            // assert
            .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeletarProcedimento() throws Exception {
        // act
        ControllerTestUtils
            .deleteMapping(mvc, (BASE_URL + "/1"))
            
            // assert
            .andExpect(status().isNoContent());
    }
    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"USER", "CLIENT"})
    void testDeletarProcedimentoComCredenciaisInvalidas() throws Exception {
        // act
        ControllerTestUtils
            .deleteMapping(mvc, (BASE_URL + "/1"))
            
            // assert
            .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }
}