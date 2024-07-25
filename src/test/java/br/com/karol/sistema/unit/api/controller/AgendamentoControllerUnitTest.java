package br.com.karol.sistema.unit.api.controller;

import static org.hamcrest.Matchers.hasSize;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

import br.com.karol.sistema.api.controller.AgendamentoController;
import br.com.karol.sistema.api.dto.agendamento.AtualizarObservacaoAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.AtualizarStatusAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.ClienteCriarAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.CriarAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.DadosAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.DadosBasicosAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.MeDadosAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.ObservacaoAtualizadaAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.RemarcarAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.StatusAtualizadoAgendamentoDTO;
import br.com.karol.sistema.api.mapper.AgendamentoMapper;
import br.com.karol.sistema.business.service.AgendamentoService;
import br.com.karol.sistema.business.service.ClienteService;
import br.com.karol.sistema.business.service.DisponibilidadeService;
import br.com.karol.sistema.business.service.ProcedimentoService;
import br.com.karol.sistema.business.service.TokenService;
import br.com.karol.sistema.config.ContextualizeUsuarioTypeWithRoles;
import br.com.karol.sistema.constants.TestConstants;
import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.domain.enums.StatusAgendamento;
import br.com.karol.sistema.domain.validator.agendamento.AgendamentoValidator;
import br.com.karol.sistema.infra.repository.AgendamentoRepository;
import br.com.karol.sistema.infra.repository.UsuarioRepository;
import br.com.karol.sistema.infra.security.SecurityConfig;
import br.com.karol.sistema.unit.utils.AgendamentoUtils;
import br.com.karol.sistema.unit.utils.ControllerTestUtils;

@AutoConfigureJsonTesters
@Import(SecurityConfig.class)
@WebMvcTest(AgendamentoController.class)
public class AgendamentoControllerUnitTest {

    private static final String BASE_URL = "/agendamentos";
    private static final String CLIENT_ROUTE = BASE_URL + "/me";

    private static final Agendamento AGENDAMENTO_DEFAULT = AgendamentoUtils.getAgendamento();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<CriarAgendamentoDTO> criarAgendamentoDTOJson;
    @Autowired
    private JacksonTester<ClienteCriarAgendamentoDTO> clienteCriarAgendamentoDTOJson;
    @Autowired
    private JacksonTester<RemarcarAgendamentoDTO> remarcarAgendamentoDTOJson;
    @Autowired
    private JacksonTester<AtualizarObservacaoAgendamentoDTO> atualizarObservacaoAgendamentoDTOJson;
    @Autowired
    private JacksonTester<AtualizarStatusAgendamentoDTO> atualizarStatusAgendamentoDTOJson;

    @MockBean
    private AgendamentoService service;
    @MockBean
    private DisponibilidadeService disponibilidadeService;
    @MockBean
    private AgendamentoRepository agendamentoRepository;
    @MockBean
    private ClienteService clienteService;
    @MockBean
    private ProcedimentoService procedimentoService;
    @MockBean
    private AgendamentoMapper mapper;
    @MockBean
    private List<AgendamentoValidator> validators;

    // Dependências do SecurityFilter
    @MockBean
    private TokenService tokenService;
    @MockBean
    private UsuarioRepository usuarioRepository;


    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"USER", "ADMIN"})
    void testCriarAgendamento_comRolesAutorizadas() throws IOException, Exception {
        // arrange
        var requestBody = new CriarAgendamentoDTO(
            AGENDAMENTO_DEFAULT.getProcedimento().getId(),
            AGENDAMENTO_DEFAULT.getStatus(),
            AGENDAMENTO_DEFAULT.getObservacao(),
            AGENDAMENTO_DEFAULT.getCliente().getId(),
            AGENDAMENTO_DEFAULT.getDataHora()
        );
        
        var responseBody = new DadosAgendamentoDTO(AGENDAMENTO_DEFAULT);
        when(service.salvarAgendamento(any(), any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.postRequest(mvc, BASE_URL, criarAgendamentoDTOJson.write(requestBody).getJson())
            // assert
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.status").exists())
            .andExpect(jsonPath("$.observacao").exists())
            .andExpect(jsonPath("$.dataHora").exists())
            .andExpect(jsonPath("$.procedimento").exists())
            .andExpect(jsonPath("$.procedimento.id").exists())
            .andExpect(jsonPath("$.procedimento.nome").exists())
            .andExpect(jsonPath("$.procedimento.descricao").exists())
            .andExpect(jsonPath("$.procedimento.duracao").exists())
            .andExpect(jsonPath("$.procedimento.valor").exists())
            .andExpect(jsonPath("$.cliente").exists())
            .andExpect(jsonPath("$.cliente.id").exists())
            .andExpect(jsonPath("$.cliente.nome").exists())
            .andExpect(jsonPath("$.cliente.telefone").exists())
            .andExpect(jsonPath("$.cliente.email").exists())
            .andExpect(jsonPath("$.dataCriacao").exists())
            .andExpect(jsonPath("$.dataModificacao").exists())
            .andExpect(jsonPath("$.usuarioLogin").exists())
            .andExpect(result -> {
                var nullableHeaderRedirect = Optional.of(result.getResponse().getHeader("Location"));

                var redirect = nullableHeaderRedirect
                    .orElseThrow(() -> new AssertionError("Redirect URL is null"));
                if (redirect.isBlank())
                throw new AssertionError("Redirect URL is blank");
            });

        verify(service).salvarAgendamento(any(), any());
    }
    @Test
    @WithMockUser
    void testCriaCliente_comBodyInvalido() throws IOException, Exception {
        // arrange
        var requestBody = new CriarAgendamentoDTO(
            null, 
            null, 
            null, 
            null, 
            TestConstants.PASSADO);
        when(service.salvarAgendamento(any(), any())).thenReturn(new DadosAgendamentoDTO(AGENDAMENTO_DEFAULT));

        // act
        ControllerTestUtils.postRequest(mvc, BASE_URL, criarAgendamentoDTOJson.write(requestBody).getJson())
            // assert
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").exists())
            .andExpect(jsonPath("$.fields").exists())
            .andExpect(jsonPath("$.fields.procedimentoId").exists())
            .andExpect(jsonPath("$.fields.status").exists())
            .andExpect(jsonPath("$.fields.observacao").doesNotExist()) // atributo sem validação, valor padrão é blank
            .andExpect(jsonPath("$.fields.clienteId").exists())
            .andExpect(jsonPath("$.fields.dataHora").exists());

        verifyNoInteractions(service);
    }
    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"CLIENT"})
    void testCriaCliente_comRoleNaoAutorizada() throws IOException, Exception {
        // arrange
        var requestBody = new CriarAgendamentoDTO(
            AGENDAMENTO_DEFAULT.getProcedimento().getId(),
            AGENDAMENTO_DEFAULT.getStatus(),
            AGENDAMENTO_DEFAULT.getObservacao(),
            AGENDAMENTO_DEFAULT.getCliente().getId(),
            AGENDAMENTO_DEFAULT.getDataHora()
        );

        // act
        ControllerTestUtils.postRequest(mvc, BASE_URL, criarAgendamentoDTOJson.write(requestBody).getJson())
            // assert
            .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }
    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"CLIENT"})
    void testCriarAgendamentoMe_comRolesAutorizadas() throws IOException, Exception {
        // arrange
        var requestBody = new ClienteCriarAgendamentoDTO(
            AGENDAMENTO_DEFAULT.getProcedimento().getId(),
            AGENDAMENTO_DEFAULT.getStatus(),
            AGENDAMENTO_DEFAULT.getObservacao(),
            AGENDAMENTO_DEFAULT.getDataHora()
        );
        
        var responseBody = new MeDadosAgendamentoDTO(AGENDAMENTO_DEFAULT);
        when(service.salvarAgendamentoMe(any(), any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.postRequest(mvc, CLIENT_ROUTE, clienteCriarAgendamentoDTOJson.write(requestBody).getJson())
            // assert
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.status").exists())
            .andExpect(jsonPath("$.observacao").exists())
            .andExpect(jsonPath("$.dataHora").exists())
            .andExpect(jsonPath("$.procedimento").exists())
            .andExpect(jsonPath("$.procedimento.id").exists())
            .andExpect(jsonPath("$.procedimento.nome").exists())
            .andExpect(jsonPath("$.procedimento.descricao").exists())
            .andExpect(jsonPath("$.procedimento.duracao").exists())
            .andExpect(jsonPath("$.procedimento.valor").exists())
            .andExpect(result -> {
                var nullableHeaderRedirect = Optional.of(result.getResponse().getHeader("Location"));

                var redirect = nullableHeaderRedirect
                    .orElseThrow(() -> new AssertionError("Redirect URL is null"));
                if (redirect.isBlank())
                throw new AssertionError("Redirect URL is blank");
            });

        verify(service).salvarAgendamentoMe(any(), any());
    }
    @Test
    @WithMockUser(roles = "CLIENT")
    void testCriaCliente_comBodyInvalidoMe() throws IOException, Exception {
        // arrange
        var requestBody = new ClienteCriarAgendamentoDTO(
            null, 
            null, 
            null, 
            TestConstants.PASSADO);
        
        when(service.salvarAgendamento(any(), any())).thenReturn(new DadosAgendamentoDTO(AGENDAMENTO_DEFAULT));

        // act
        ControllerTestUtils.postRequest(mvc, CLIENT_ROUTE, clienteCriarAgendamentoDTOJson.write(requestBody).getJson())
            // assert
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").exists())
            .andExpect(jsonPath("$.fields").exists())
            .andExpect(jsonPath("$.fields.procedimentoId").exists())
            .andExpect(jsonPath("$.fields.status").exists())
            .andExpect(jsonPath("$.fields.observacao").doesNotExist()) // atributo sem validação, valor padrão é blank
            .andExpect(jsonPath("$.fields.dataHora").exists());

        verifyNoInteractions(service);
    }
    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"USER", "ADMIN"})
    void testCriaCliente_comRoleNaoAutorizadaMe() throws IOException, Exception {
        // arrange
        var requestBody = new CriarAgendamentoDTO(
            AGENDAMENTO_DEFAULT.getProcedimento().getId(),
            AGENDAMENTO_DEFAULT.getStatus(),
            AGENDAMENTO_DEFAULT.getObservacao(),
            AGENDAMENTO_DEFAULT.getCliente().getId(),
            AGENDAMENTO_DEFAULT.getDataHora()
        );

        // act
        ControllerTestUtils.postRequest(mvc, CLIENT_ROUTE, criarAgendamentoDTOJson.write(requestBody).getJson())
            // assert
            .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }

    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"USER", "ADMIN"})
    void testListarAgendamento_comRetornoValido() throws IOException, Exception {
        // arrange
        var responseBody = new PageImpl<>(List.of(new DadosBasicosAgendamentoDTO(AGENDAMENTO_DEFAULT)));
        when(service.listarTodosAgendamentos(any(), any(), any(), any(), any(), any(), any(), any(), any()))
            .thenReturn(responseBody);

        // act
        ControllerTestUtils.getRequest(mvc, BASE_URL)
            // assert
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].id").exists())
            .andExpect(jsonPath("$.content[0].status").exists())
            .andExpect(jsonPath("$.content[0].dataHora").exists())
            .andExpect(jsonPath("$.content[0].procedimento").exists())
            .andExpect(jsonPath("$.content[0].procedimento.id").exists())
            .andExpect(jsonPath("$.content[0].procedimento.nome").exists())
            .andExpect(jsonPath("$.content[0].procedimento.descricao").exists())
            .andExpect(jsonPath("$.content[0].procedimento.duracao").exists())
            .andExpect(jsonPath("$.content[0].procedimento.valor").exists())
            .andExpect(jsonPath("$.content[0].cliente").exists())
            .andExpect(jsonPath("$.content[0].cliente.id").exists())
            .andExpect(jsonPath("$.content[0].cliente.nome").exists())
            .andExpect(jsonPath("$.content[0].cliente.telefone").exists())
            .andExpect(jsonPath("$.content[0].cliente.email").exists());

        verify(service).listarTodosAgendamentos(any(), any(), any(), any(), any(), any(), any(), any(), any());
    }

    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"CLIENT"})
    void testListarAgendamento_comRoleNaoAutorizada() throws IOException, Exception {
        // act
        ControllerTestUtils.getRequest(mvc, BASE_URL)
            // assert
            .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }
    @Test
    @WithMockUser("ADMIN")
    void testarListarAgendamento_comQueryParams() throws IOException, Exception {
        // arrange
        var responseBody = new PageImpl<>(List.of(new DadosBasicosAgendamentoDTO(AGENDAMENTO_DEFAULT)));
        when(service.listarTodosAgendamentos(
            eq(1L),  // Procedimento ID
            eq("procedimentoNome"), // nome do Procedimento
            eq(StatusAgendamento.PENDENTE),
            any(LocalDateTime.class),
            any(LocalDateTime.class),
            eq("clienteNome"),
            eq(2L), // Cliente ID
            eq("12345678901"),
            any(Pageable.class)
            )
        ).thenReturn(responseBody);

        // act
        mvc.perform(
            get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("idProcedimento", "1")
                .param("nomeProcedimento", "procedimentoNome")
                .param("status", StatusAgendamento.PENDENTE.toString())
                .param("minDataHora", LocalDateTime.now().minusDays(1).toString())
                .param("maxDataHora", LocalDateTime.now().plusDays(1).toString())
                .param("nomeCliente", "clienteNome")
                .param("idCliente", "2")
                .param("cpfCliente", "12345678901")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "dataHora,desc")
            )
            // assert
            .andExpect(status().isOk());

        verify(service).listarTodosAgendamentos(
            eq(1L),
            eq("procedimentoNome"),
            eq(StatusAgendamento.PENDENTE),
            any(LocalDateTime.class),
            any(LocalDateTime.class),
            eq("clienteNome"),
            eq(2L),
            eq("12345678901"),
            any(Pageable.class)
        );
    }

    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"USER", "ADMIN"})
    void testMostrarAgendamento_comRolesAutorizadas() throws IOException, Exception {
        // arrange
        var responseBody = new DadosAgendamentoDTO(AGENDAMENTO_DEFAULT);
        when(service.buscarAgendamentoPorId(any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.getRequest(mvc, BASE_URL + "/1")
            // assert
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.status").exists())
            .andExpect(jsonPath("$.observacao").exists())
            .andExpect(jsonPath("$.dataHora").exists())
            .andExpect(jsonPath("$.procedimento").exists())
            .andExpect(jsonPath("$.procedimento.id").exists())
            .andExpect(jsonPath("$.procedimento.nome").exists())
            .andExpect(jsonPath("$.procedimento.descricao").exists())
            .andExpect(jsonPath("$.procedimento.duracao").exists())
            .andExpect(jsonPath("$.procedimento.valor").exists())
            .andExpect(jsonPath("$.cliente").exists())
            .andExpect(jsonPath("$.cliente.id").exists())
            .andExpect(jsonPath("$.cliente.nome").exists())
            .andExpect(jsonPath("$.cliente.telefone").exists())
            .andExpect(jsonPath("$.cliente.email").exists())
            .andExpect(jsonPath("$.dataCriacao").exists())
            .andExpect(jsonPath("$.dataModificacao").exists())
            .andExpect(jsonPath("$.usuarioLogin").exists());

        verify(service).buscarAgendamentoPorId(any());
    }

    @Test
    @WithMockUser
    void testBuscarHorariosDisponiveis() throws IOException, Exception {
        // arrange
        var responseBody = List.of(LocalDateTime.now());
        when(disponibilidadeService.filtrarHorariosDisponiveis(any(), any())).thenReturn(responseBody);

        // act
        mvc.perform(
            get(BASE_URL + "/disponibilidade")
                .contentType(MediaType.APPLICATION_JSON)
                .param("procedimentoId", "1")
                .param("diaHora", "2024-07-11")
            )
            // assert
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(responseBody.size())));

        verify(disponibilidadeService).filtrarHorariosDisponiveis(any(), any());
    }
    @Test
    @WithMockUser
    void testBuscarHorariosDisponiveis_semParametrosObrigatorios() throws IOException, Exception {
        // act
        mvc.perform(
            get(BASE_URL + "/disponibilidade")
                .contentType(MediaType.APPLICATION_JSON)
            )
            // assert
            .andExpect(status().isBadRequest());

        verifyNoInteractions(disponibilidadeService);
    }
    @Test
    @WithMockUser
    void testBuscarHorariosDisponiveis_comParametrosEmFormatoErrado01() throws IOException, Exception {
        // act
        mvc.perform(
            get(BASE_URL + "/disponibilidade")
                .contentType(MediaType.APPLICATION_JSON)
                .param("procedimentoId", "1xpto")
                .param("diaHora", "2024-12-11")
            )
            // assert
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").exists())
            .andExpect(jsonPath("$.param.procedimentoId").exists());

        verifyNoInteractions(disponibilidadeService);
    }
    @Test
    @WithMockUser
    void testBuscarHorariosDisponiveis_comParametrosEmFormatoErrado02() throws IOException, Exception {
        // act
        mvc.perform(
            get(BASE_URL + "/disponibilidade")
                .contentType(MediaType.APPLICATION_JSON)
                .param("procedimentoId", "1")
                .param("diaHora", "2024-13-11")
            )
            // assert
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").exists())
            .andExpect(jsonPath("$.param.diaHora").exists());

        verifyNoInteractions(disponibilidadeService);
    }

    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"USER", "ADMIN"})
    void testRemarcarAgendamento() throws IOException, Exception {
        // arrange
        var requestBody = new RemarcarAgendamentoDTO(AGENDAMENTO_DEFAULT.getDataHora());
        var responseBody = new DadosAgendamentoDTO(AGENDAMENTO_DEFAULT);
        when(service.editarDataHoraAgendamento(any(), any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.patchRequest(
            mvc, 
            BASE_URL + "/1", 
            remarcarAgendamentoDTOJson.write(requestBody).getJson()
        )
        // assert
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.status").exists())
        .andExpect(jsonPath("$.observacao").exists())
        .andExpect(jsonPath("$.dataHora").exists())
        .andExpect(jsonPath("$.procedimento").exists())
        .andExpect(jsonPath("$.procedimento.id").exists())
        .andExpect(jsonPath("$.procedimento.nome").exists())
        .andExpect(jsonPath("$.procedimento.descricao").exists())
        .andExpect(jsonPath("$.procedimento.duracao").exists())
        .andExpect(jsonPath("$.procedimento.valor").exists())
        .andExpect(jsonPath("$.cliente").exists())
        .andExpect(jsonPath("$.cliente.id").exists())
        .andExpect(jsonPath("$.cliente.nome").exists())
        .andExpect(jsonPath("$.cliente.telefone").exists())
        .andExpect(jsonPath("$.cliente.email").exists())
        .andExpect(jsonPath("$.usuarioLogin").exists())
        .andExpect(jsonPath("$.dataCriacao").exists())
        .andExpect(jsonPath("$.dataModificacao").exists());

        verify(service).editarDataHoraAgendamento(any(), any());
    }
    @Test
    @WithMockUser
    void testRemarcarAgendamento_comBodyInvalido() throws IOException, Exception {
        // arrange
        var requestBody = new RemarcarAgendamentoDTO(TestConstants.PASSADO);

        // act
        ControllerTestUtils.patchRequest(
            mvc, 
            BASE_URL + "/agendamentoIdxpto", 
            remarcarAgendamentoDTOJson.write(requestBody).getJson()
        )
        // assert
        .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }
    @Test
    @WithMockUser(roles = "CLIENT")
    void testRemarcarAgendamento_comRoleNaoAutorizada() throws IOException, Exception {
        // arrange
        var requestBody = new RemarcarAgendamentoDTO(null);

        // act
        ControllerTestUtils.patchRequest(mvc, BASE_URL + "/agendamentoIdxpto", remarcarAgendamentoDTOJson.write(requestBody).getJson())
            // assert
            .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }

    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"USER", "ADMIN"})
    void testAtualizarObservacaoAgendamento_comRolesAutorizadas() throws IOException, Exception {
        // arrange
        var requestBody = new AtualizarObservacaoAgendamentoDTO("nova observacao");
        var responseBody = new ObservacaoAtualizadaAgendamentoDTO(AGENDAMENTO_DEFAULT);
        when(service.editarObservacaoAgendamento(any(), any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.patchRequest(
            mvc, 
            BASE_URL + "/1/observacao", 
            atualizarObservacaoAgendamentoDTOJson.write(requestBody).getJson()
        )
        // assert
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.observacao").exists());

        verify(service).editarObservacaoAgendamento(any(), any());
    }
    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"CLIENT"})
    void testAtualizarObservacaoAgendamento_comRoleNaoAutorizada() throws IOException, Exception {
        // arrange
        var requestBody = new AtualizarObservacaoAgendamentoDTO("nova observacao");
        var responseBody = new ObservacaoAtualizadaAgendamentoDTO(AGENDAMENTO_DEFAULT);
        when(service.editarObservacaoAgendamento(any(), any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.patchRequest(
            mvc, 
            BASE_URL + "/1/observacao", 
            atualizarObservacaoAgendamentoDTOJson.write(requestBody).getJson()
        )
        // assert
        .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }
    @Test
    @WithMockUser
    void testAtualizarObservacaoAgendamento_comBodyInvalido() throws IOException, Exception {
        // arrange
        var requestBody = new AtualizarObservacaoAgendamentoDTO(null);

        // act
        ControllerTestUtils.patchRequest(
            mvc, 
            BASE_URL + "/agendamentoIdxpto/observacao", 
            atualizarObservacaoAgendamentoDTOJson.write(requestBody).getJson()
        )
        // assert
        .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }


    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"USER", "ADMIN"})
    void testAtualizarStatusAgendamento_comRolesAutorizadas() throws IOException, Exception {
        // arrange
        var requestBody = new AtualizarStatusAgendamentoDTO(StatusAgendamento.CONFIRMADO);
        var responseBody = new StatusAtualizadoAgendamentoDTO(AGENDAMENTO_DEFAULT);
        when(service.editarStatusAgendamento(any(), any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.patchRequest(
            mvc, 
            BASE_URL + "/1/status", 
            atualizarStatusAgendamentoDTOJson.write(requestBody).getJson()
        )
        // assert
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.status").exists());

        verify(service).editarStatusAgendamento(any(), any());
    }
    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"CLIENT"})
    void testAtualizarStatusAgendamento_comRoleNaoAutorizadas() throws IOException, Exception {
        // arrange
        var requestBody = new AtualizarStatusAgendamentoDTO(StatusAgendamento.CONFIRMADO);

        // act
        ControllerTestUtils.patchRequest(
            mvc, 
            BASE_URL + "/1/status", 
            atualizarStatusAgendamentoDTOJson.write(requestBody).getJson()
        )
        // assert
        .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }
    @Test
    @WithMockUser
    void testAtualizarStatusAgendamento_comBodyInvalido() throws IOException, Exception {
        var requestBody = new AtualizarStatusAgendamentoDTO(null);
        var responseBody = new StatusAtualizadoAgendamentoDTO(AGENDAMENTO_DEFAULT);
        when(service.editarStatusAgendamento(any(), any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.patchRequest(
            mvc, 
            BASE_URL + "/agendamentoIdxpto/status", 
            atualizarStatusAgendamentoDTOJson.write(requestBody).getJson()
        )
        // assert
        .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }

    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"USER", "ADMIN"})
    void testDeletarAgendamento_comRolesAutorizadas() throws IOException, Exception {
        // act
        ControllerTestUtils.deleteMapping(mvc, BASE_URL + "/1")
            // assert
            .andExpect(status().isNoContent());

        verify(service).removerAgendamento(any());
    }
    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"CLIENT"})
    void testDeletarAgendamentoComRolesNaoAutorizadas() throws IOException, Exception {
        // act
        ControllerTestUtils.deleteMapping(mvc, BASE_URL + "/1")
            // assert
            .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }

    // -- Rotas de Cliente

    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"CLIENT"})
    void testListarAgendamentoMe_comRolesAutorizadas() throws IOException, Exception {
        // arrange
        var responseBody = new PageImpl<>(List.of(new DadosBasicosAgendamentoDTO(AGENDAMENTO_DEFAULT)));
        when(service.listarTodosAgendamentosUsuarioAtual(anyLong(), any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.getRequest(mvc, CLIENT_ROUTE)
            // assert
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].id").exists())
            .andExpect(jsonPath("$.content[0].status").exists())
            .andExpect(jsonPath("$.content[0].dataHora").exists())
            .andExpect(jsonPath("$.content[0].procedimento").exists())
            .andExpect(jsonPath("$.content[0].procedimento.id").exists())
            .andExpect(jsonPath("$.content[0].procedimento.nome").exists())
            .andExpect(jsonPath("$.content[0].procedimento.descricao").exists())
            .andExpect(jsonPath("$.content[0].procedimento.duracao").exists())
            .andExpect(jsonPath("$.content[0].procedimento.valor").exists())
            .andExpect(jsonPath("$.content[0].cliente").exists())
            .andExpect(jsonPath("$.content[0].cliente.id").exists())
            .andExpect(jsonPath("$.content[0].cliente.nome").exists())
            .andExpect(jsonPath("$.content[0].cliente.telefone").exists())
            .andExpect(jsonPath("$.content[0].cliente.email").exists());

        verify(service).listarTodosAgendamentosUsuarioAtual(any(), any());
    }
    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"USER", "ADMIN"})
    void testListarAgendamentoMe_comRoleNaoAutorizada() throws IOException, Exception {
        // act
        ControllerTestUtils.getRequest(mvc, CLIENT_ROUTE)
            // assert
            .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }

    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"CLIENT"})
    void testMostrarAgendamentoMe_comRolesAutorizadas() throws IOException, Exception {
        // arrange
        var responseBody = new MeDadosAgendamentoDTO(AGENDAMENTO_DEFAULT);
        when(service.buscarAgendamentoPorIdEUsuarioId(anyLong(), any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.getRequest(mvc, CLIENT_ROUTE + "/1")
            // assert
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.status").exists())
            .andExpect(jsonPath("$.observacao").exists())
            .andExpect(jsonPath("$.dataHora").exists())
            .andExpect(jsonPath("$.procedimento").exists())
            .andExpect(jsonPath("$.procedimento.id").exists())
            .andExpect(jsonPath("$.procedimento.nome").exists())
            .andExpect(jsonPath("$.procedimento.descricao").exists())
            .andExpect(jsonPath("$.procedimento.duracao").exists())
            .andExpect(jsonPath("$.procedimento.valor").exists());

        verify(service).buscarAgendamentoPorIdEUsuarioId(anyLong(), any());
    }
    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"USER", "ADMIN"})
    void testMostrarAgendamentoMe_comRolesNaoAutorizadas() throws IOException, Exception {
        // act
        ControllerTestUtils.getRequest(mvc, CLIENT_ROUTE + "/1")
            // assert
            .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }

    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"CLIENT"})
    void testRemarcarAgendamentoMe_comRolesAutorizadas() throws IOException, Exception {
        // arrange
        var requestBody = new RemarcarAgendamentoDTO(AGENDAMENTO_DEFAULT.getDataHora());
        var responseBody = new MeDadosAgendamentoDTO(AGENDAMENTO_DEFAULT);
        when(service.editarDataHoraAgendamentoUsuarioAtual(any(), any(), any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.patchRequest(
            mvc, 
            CLIENT_ROUTE + "/1", 
            remarcarAgendamentoDTOJson.write(requestBody).getJson()
        )
        // assert
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.status").exists())
        .andExpect(jsonPath("$.observacao").exists())
        .andExpect(jsonPath("$.dataHora").exists())
        .andExpect(jsonPath("$.procedimento").exists())
        .andExpect(jsonPath("$.procedimento.id").exists())
        .andExpect(jsonPath("$.procedimento.nome").exists())
        .andExpect(jsonPath("$.procedimento.descricao").exists())
        .andExpect(jsonPath("$.procedimento.duracao").exists())
        .andExpect(jsonPath("$.procedimento.valor").exists());

        verify(service).editarDataHoraAgendamentoUsuarioAtual(any(), any(), any());
    }
    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"USER", "ADMIN"})
    void testRemarcarAgendamentoMe_comRoleNaoAutorizada() throws IOException, Exception {
        // arrange
        var requestBody = new RemarcarAgendamentoDTO(AGENDAMENTO_DEFAULT.getDataHora());

        // act
        ControllerTestUtils.patchRequest(mvc, CLIENT_ROUTE + "/1", remarcarAgendamentoDTOJson.write(requestBody).getJson())
            // assert
            .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }
    @Test
    @WithMockUser
    void testRemarcarAgendamentoMe_comBodyInvalido() throws IOException, Exception {
        // arrange
        var requestBody = new RemarcarAgendamentoDTO(TestConstants.PASSADO);

        // act
        ControllerTestUtils.patchRequest(
            mvc, 
            BASE_URL + "/1", 
            remarcarAgendamentoDTOJson.write(requestBody).getJson()
        )
        // assert
        .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }

    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"CLIENT"})
    void testAtualizarObservacaoMeAgendamento_comRolesAutorizadas() throws IOException, Exception {
        // arrange
        var requestBody = new AtualizarObservacaoAgendamentoDTO("nova observacao");
        var responseBody = new ObservacaoAtualizadaAgendamentoDTO(AGENDAMENTO_DEFAULT);
        when(service.editarObservacaoAgendamentoUsuarioAtual(any(), any(), any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.patchRequest(
            mvc, 
            CLIENT_ROUTE + "/1/observacao", 
            atualizarObservacaoAgendamentoDTOJson.write(requestBody).getJson()
        )
        // assert
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.observacao").exists());

        verify(service).editarObservacaoAgendamentoUsuarioAtual(anyLong(), any(), any());
    }
    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"USER", "ADMIN"})
    void testAtualizarObservacaoMeAgendamento_comRoleSNaoAutorizadas() throws IOException, Exception {
        // arrange
        var requestBody = new AtualizarObservacaoAgendamentoDTO("nova observacao");

        // act
        ControllerTestUtils.patchRequest(
            mvc, 
            CLIENT_ROUTE + "/1/observacao", 
            atualizarObservacaoAgendamentoDTOJson.write(requestBody).getJson()
        )
        // assert
        .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }
    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"CLIENT"})
    void testAtualizarObservacaoMeAgendamento_comBodyInvalido() throws IOException, Exception {
        // arrange
        var requestBody = new AtualizarObservacaoAgendamentoDTO(null);

        // act
        ControllerTestUtils.patchRequest(
            mvc, 
            CLIENT_ROUTE + "/agendamentoIdxpto/observacao", 
            atualizarObservacaoAgendamentoDTOJson.write(requestBody).getJson()
        )
        // assert
        .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }

    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"CLIENT"})
    void testAtualizarStatusAgendamentoMe_comRoleAutorizada() throws IOException, Exception {
        // arrange
        var requestBody = new AtualizarStatusAgendamentoDTO(StatusAgendamento.CONFIRMADO);
        var responseBody = new StatusAtualizadoAgendamentoDTO(AGENDAMENTO_DEFAULT);
        when(service.editarStatusAgendamentoUsuarioAtual(any(), any(), any())).thenReturn(responseBody);

        // act
        ControllerTestUtils.patchRequest(
            mvc, 
            CLIENT_ROUTE + "/1/status", 
            atualizarStatusAgendamentoDTOJson.write(requestBody).getJson()
        )
        // assert
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.status").exists());

        verify(service).editarStatusAgendamentoUsuarioAtual(any(), any(), any());
    }
    @TestTemplate
    @ContextualizeUsuarioTypeWithRoles(roles = {"USER", "ADMIN"})
    void testAtualizarStatusAgendamento_comRolesNaoAutorizadas() throws IOException, Exception {
        // arrange
        var requestBody = new AtualizarStatusAgendamentoDTO(StatusAgendamento.CONFIRMADO);

        // act
        ControllerTestUtils.patchRequest(
            mvc, 
            CLIENT_ROUTE + "/1/status", 
            atualizarStatusAgendamentoDTOJson.write(requestBody).getJson()
        )
        // assert
        .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }
    @Test
    @WithMockUser(roles = "CLIENT")
    void testAtualizarStatusAgendamentoMe_comBodyInvalido() throws IOException, Exception {
        var requestBody = new AtualizarStatusAgendamentoDTO(null);

        // act
        ControllerTestUtils.patchRequest(
            mvc, 
            CLIENT_ROUTE + "/agendamentoIdxpto/status", 
            atualizarStatusAgendamentoDTOJson.write(requestBody).getJson()
        )
        // assert
        .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }
}