package br.com.karol.sistema.service;

import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.domain.Procedimento;
import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.dto.RelatorioDTO;
import br.com.karol.sistema.repository.RelatorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

@Service
@Transactional
public class RelatorioService {
    @Autowired
    private RelatorioRepository repository;

    public void criarTxt() {
        Path path = Path.of("C:/Users/Junior/Desktop/relatorios/text.txt");
        RelatorioDTO relatorio = new RelatorioDTO();
        Cliente cliente = relatorio.getCliente();
        Agendamento agendamento = relatorio.getAgendamento();
        Procedimento procedimento = relatorio.getProcedimento();
        String observacao = relatorio.getObservacao();
        LocalDate dataCadastro = relatorio.getDataCadastro();
        Usuario responsavelAgendamento = relatorio.getResponsavelAgendamento();
        // Formatar o conteúdo
        StringBuilder content = new StringBuilder();
        content.append("== RELATÓRIO ==\n")
                .append("Cliente: ").append(cliente.getNome()).append("\n")
                .append("Agendamento: ").append(agendamento.getDataHora()).append("\n")
                .append("Procedimento: ").append(procedimento.getDescricao()).append("\n")
                .append("Observação: ").append(observacao).append("\n")
                .append("Data de Cadastro: ").append(dataCadastro).append("\n")
                .append("Responsável pelo Agendamento: ").append(responsavelAgendamento.getNome()).append("\n");

        // Escrever no arquivo
        try {
            Files.writeString(path, content.toString());
            System.out.println("Arquivo criado com sucesso em: " + path.toString());
        } catch (IOException e) {
            System.err.println("Erro ao criar o arquivo: " + e.getMessage());
        }
    }
}