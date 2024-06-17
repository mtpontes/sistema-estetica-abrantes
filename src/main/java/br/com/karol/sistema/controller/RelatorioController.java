package br.com.karol.sistema.controller;

import br.com.karol.sistema.dto.RelatorioDTO;
import br.com.karol.sistema.service.RelatorioService;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relatorio")
@Data
public class RelatorioController {
    private final RelatorioService service;

    @PostMapping("/gerarRelatorio")
    public void gerarRelatorio(@RequestBody RelatorioDTO relatorioDTO) {

    }
}
