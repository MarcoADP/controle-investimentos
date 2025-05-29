package com.github.marcoadp.controle_investimentos.controller;

import com.github.marcoadp.controle_investimentos.dto.response.*;
import com.github.marcoadp.controle_investimentos.service.CarteiraDadosService;
import com.github.marcoadp.controle_investimentos.service.ProventoDadosService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dados")
@RequiredArgsConstructor
public class DadosController {

    private final CarteiraDadosService carteiraDadosService;

    private final ProventoDadosService proventoDadosService;

    @GetMapping("/resumo/{id}")
    public DadosResumoResponse buscarResumo(@PathVariable Long id) {
        return carteiraDadosService.buscarResumo(id);
    }

    @GetMapping("/carteira-simplificada/{id}")
    public CarteiraSimplificadaResponse buscarCarteiraSimplificada(@PathVariable Long id) {
        return carteiraDadosService.buscarCarteiraSimplificada(id);
    }

    @GetMapping("/patrimonio/evolucao/{id}")
    public List<PatrimonioEvolucaoResponse> calcularEvolucaoPatrimonio(@PathVariable Long id,
                                                                       @RequestParam(required = false) Integer meses) {
        return carteiraDadosService.buscarPatrimonioEvolucao(id, meses);
    }

    @GetMapping("/carteira/proporcao/{id}")
    public List<CarteiraProporcaoResponse> buscarCarteiraProporcao(@PathVariable Long id) {
        return carteiraDadosService.buscarProporcaoCarteira(id);
    }

    @GetMapping("/carteira/ativos/proporcao/{id}")
    public List<CarteiraProporcaoResponse> buscarCarteiraProporcao(@PathVariable Long id,
                                                                   @RequestParam(required = false) List<String> tipos) {
        return carteiraDadosService.buscarProporcaoAtivos(id, tipos);
    }

    @GetMapping("/provento-historico/{id}")
    public ProventoHistoricoResponse buscarProventoHistorico(@PathVariable Long id,
                                                             @RequestParam(required = false) Integer anoInicio,
                                                             @RequestParam(required = false) Integer anoFim
    ) {
        return proventoDadosService.buscarProventoHistorico(id, anoInicio, anoFim);
    }

    @GetMapping("/provento-anual/{id}")
    public List<ProventoPeriodoResponse> buscarProventoAnual(@PathVariable Long id
    ) {
        return proventoDadosService.buscarProventoAnual(id);
    }

    @GetMapping("/provento-mensal/{id}")
    public List<ProventoPeriodoResponse> buscarProventoMensal(@PathVariable Long id,
                                                             @RequestParam(required = false) Integer ano
    ) {
        return proventoDadosService.buscarProventoMensal(id, ano);
    }


}

