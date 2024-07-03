package br.com.karol.sistema.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.karol.sistema.dto.RelatorioDTO;
import br.com.karol.sistema.service.RelatorioService;

@RestController
@RequestMapping("/relatorio")
public class RelatorioController {
    
    private final RelatorioService service;

    public RelatorioController(RelatorioService service) {
        this.service = service;
    }

    
    @PostMapping
    public void gerarRelatorio(@RequestBody RelatorioDTO relatorioDTO) {
    }
}