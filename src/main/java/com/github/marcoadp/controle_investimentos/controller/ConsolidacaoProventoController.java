package com.github.marcoadp.controle_investimentos.controller;

import com.github.marcoadp.controle_investimentos.dto.response.ConsolidacaoProventoAnualResponse;
import com.github.marcoadp.controle_investimentos.dto.response.ConsolidacaoProventoResponse;
import com.github.marcoadp.controle_investimentos.mapper.ConsolidacaoProventoMapper;
import com.github.marcoadp.controle_investimentos.service.ConsolidacaoProventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consolidacao-provento")
@RequiredArgsConstructor
public class ConsolidacaoProventoController {

    private final ConsolidacaoProventoService consolidacaoService;
    private final ConsolidacaoProventoMapper consolidacaoMapper;

    @PostMapping("/carteiras")
    public List<ConsolidacaoProventoResponse> consolidarCarteiras() {
        var consolidacoes = consolidacaoService.consolidarCarteiras();
        return consolidacoes.stream().map(consolidacaoMapper::toConsolidacaoProventoResponse).toList();
    }

    @GetMapping("/{id}")
    public ConsolidacaoProventoResponse buscarPeloId(@PathVariable Long id) {
        var consolidacao = consolidacaoService.buscarPeloId(id);
        return consolidacaoMapper.toConsolidacaoProventoResponse(consolidacao);
    }

    @GetMapping("/codigo/{codigo}")
    public List<ConsolidacaoProventoResponse> buscarPeloCodigo(@PathVariable String codigo) {
        var consolidacoes = consolidacaoService.buscarPeloCodigo(codigo);
        return consolidacoes.stream().map(consolidacaoMapper::toConsolidacaoProventoResponse).toList();
    }

    @GetMapping("/anual/codigo/{codigo}/ano/{ano}")
    public ConsolidacaoProventoAnualResponse buscarConsolidacaoAnualPeloCodigo(@PathVariable String codigo, @PathVariable Integer ano) {
        return consolidacaoService.buscarConsolidacaoAnualPeloCodigo(codigo, ano);
    }

    @GetMapping("/codigo/{codigo}/ano/{ano}")
    public List<ConsolidacaoProventoResponse> buscarPeloCodigoEAno(@PathVariable String codigo, @PathVariable Integer ano) {
        var consolidacoes = consolidacaoService.buscarPeloCodigoEAno(codigo, ano);
        return consolidacoes.stream().map(consolidacaoMapper::toConsolidacaoProventoResponse).toList();
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        consolidacaoService.remover(id);
    }



}

