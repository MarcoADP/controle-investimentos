package com.github.marcoadp.controle_investimentos.controller;

import com.github.marcoadp.controle_investimentos.dto.response.ConsolidacaoResponse;
import com.github.marcoadp.controle_investimentos.mapper.ConsolidacaoMapper;
import com.github.marcoadp.controle_investimentos.service.ConsolidacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consolidacao")
@RequiredArgsConstructor
public class ConsolidacaoController {

    private final ConsolidacaoService consolidacaoService;
    private final ConsolidacaoMapper consolidacaoMapper;

    @PostMapping("/carteiras")
    public List<ConsolidacaoResponse> consolidarCarteiras() {
        var consolidacoes = consolidacaoService.consolidarCarteiras();
        return consolidacoes.stream().map(consolidacaoMapper::toConsolidacaoResponse).toList();
    }

    @GetMapping("/{id}")
    public ConsolidacaoResponse buscarPeloId(@PathVariable Long id) {
        var consolidacao = consolidacaoService.buscarPeloId(id);
        return consolidacaoMapper.toConsolidacaoResponse(consolidacao);
    }

    @GetMapping("/codigo/{codigo}")
    public ConsolidacaoResponse buscarPeloCodigo(@PathVariable String codigo) {
        var consolidacao = consolidacaoService.buscarPeloCodigo(codigo);
        return consolidacaoMapper.toConsolidacaoResponse(consolidacao);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        consolidacaoService.remover(id);
    }



}

