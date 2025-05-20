package com.github.marcoadp.controle_investimentos.controller;

import com.github.marcoadp.controle_investimentos.dto.response.CarteiraSimplificadaResponse;
import com.github.marcoadp.controle_investimentos.dto.response.ProventoAnualResponse;
import com.github.marcoadp.controle_investimentos.dto.response.ProventoHistoricoResponse;
import com.github.marcoadp.controle_investimentos.service.DadosService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dados")
@RequiredArgsConstructor
public class DadosController {

    private final DadosService dadosService;

    @GetMapping("/carteira-simplificada/{id}")
    public CarteiraSimplificadaResponse buscarCarteiraSimplificada(@PathVariable Long id) {
        return dadosService.buscarCarteiraSimplificada(id);
    }

    @GetMapping("/provento-historico/{id}")
    public ProventoHistoricoResponse buscarProventoHistorico(@PathVariable Long id,
                                                             @RequestParam(required = false) Integer anoInicio,
                                                             @RequestParam(required = false) Integer anoFim
    ) {
        return dadosService.buscarProventoHistorico(id, anoInicio, anoFim);
    }

    @GetMapping("/provento-anual/{id}")
    public List<ProventoAnualResponse> buscarProventoAnual(@PathVariable Long id
    ) {
        return dadosService.buscarProventoAnual(id);
    }


}

